package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

abstract class MavenStep extends AbstractStep {
  Closure withMaven(Context context, Closure cl) {
    def jenkins = context.jenkins

    jenkins.withDockerContainer(
        "image": mavenImageName(context),
        "args": mavenImageArgs(context)) {
      cl(jenkins)
    }
  }

  String mavenImageName(Context context) {
    return context.pipelineParameters["maven.image.name"]
  }

  String mavenImageArgs(Context context) {
    return context.pipelineParameters["maven.image.args"]
  }
}
