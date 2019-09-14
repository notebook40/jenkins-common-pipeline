package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class BuildImageStep extends AbstractStep {
  @Override
  void process(Context context) {
    // TODO stop container if exists

    // build image
    buildImage(context)

    // remove dangling images
    removeDanglingImages(context)

    // tag image
    tagImage(context)
  }

  @Override
  boolean preStep(Context context) {
    if (!super.preStep(context)) {
      return false
    }

    if (context.pipelineParameters["skipPackage"]) {
      echo(context, 'skip package by pipeline parameters')
      return false
    }

    return true
  }

  private void buildImage(Context context) {
    echo(context, 'build image')

    // get image name from pipeline parameters
    String imageName = context.pipelineParameters["imageName"]

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
    String imageName = context.pipelineParameters["imageName"]
    String version = context.jenkins.readMavenPom().getVersion()
    context.jenkins.sh "docker tag ${imageName}:latest ${imageName}:${version}"

    if (context.pipelineParameters["tagImageWithBuildNumber"]) {
      // Tag with build number
      String buildNumber = context.jenkins.currentBuild.getNumber()
      context.jenkins.sh "docker tag ${imageName}:latest ${imageName}:${version}.${buildNumber}"
    } else {
      echo(context, 'tag with build number skipped by pipeline numbers')
    }
  }
}
