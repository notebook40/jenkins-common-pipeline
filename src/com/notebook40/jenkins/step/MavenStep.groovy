package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

abstract class MavenStep extends AbstractStep {
  String mavenImageName(Context context) {
    return context.pipelineParameters["maven.image.name"]
  }

  String mavenImageArgs(Context context) {
    return context.pipelineParameters["maven.image.args"]
  }
}
