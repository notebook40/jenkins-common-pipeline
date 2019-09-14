package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class BuildImageStep extends AbstractStep {
  @Override
  void process(Context context) {
    echo(context, 'build image')

    // TODO stop container if exists

    // get image name from pipeline parameters
    String imageName = context.pipelineParameters["imageName"]

    if (imageName) {
      // build image
      context.jenkins.docker.build imageName
    } else {
      throw new Exception("imageName is not specified in pipeline parameters")
    }
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
  }
}
