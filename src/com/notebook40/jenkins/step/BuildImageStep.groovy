package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class BuildImageStep extends AbstractStep {
  @Override
  void process(Context context) {
    // TODO stop container if exists

    // build image
    buildImage(context)

    // tag image
    tagImage(context)

    // remove dangling images
    removeDanglingImages(context)
  }

  @Override
  boolean preStep(Context context) {
    if (!super.preStep(context)) {
      return false
    }

    if (context.pipelineParameterBooleanValue("skipBuildImage")) {
      echo(context, 'skip build image by pipeline parameters')
      return false
    }

    return true
  }

  private void buildImage(Context context) {
    echo(context, 'build image')

    // get image name from pipeline parameters
    String imageName = getImageName()

    if (imageName) {
      // build image
      context.jenkins.docker.build imageName
    } else {
      throw new Exception("imageName is not specified in pipeline parameters")
    }
  }

  private void removeDanglingImages(Context context) {
    echo(context, 'remove dangling images')

    try {
      context.jenkins.sh 'docker rmi $(docker images -f "dangling=true" -q)'
    } catch (ignore) {
      // That's fine.
    }
  }

  private void tagImage(Context context) {
    echo(context, 'tag image')

    // Tag with version
    String imageName = getImageName()
    String version = context.jenkins.readMavenPom().getVersion()
    context.jenkins.sh "docker tag ${imageName}:latest ${imageName}:${version}"

    if (context.pipelineParameterBooleanValue("tagImageWithBuildNumber")) {
      // Tag with build number
      String buildNumber = context.jenkins.currentBuild.getNumber()
      context.jenkins.sh "docker tag ${imageName}:latest ${imageName}:${version}.${buildNumber}"
    } else {
      echo(context, 'tag with build number skipped by pipeline numbers')
    }
  }

  private String getImageName(Context context) {
    if (context.pipelineParameters["imageName"]) {
      return context.pipelineParameters["imageName"]
    }

    String imageName = context.jenkins.readMavenPom().getArtifactId()

    if (context.pipelineParameters["imageNamePrefix"]) {
      imageName = context.pipelineParameters["imageNamePrefix"] + imageName
    }

    return imageName
  }
}
