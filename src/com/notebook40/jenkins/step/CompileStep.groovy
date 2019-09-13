package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class CompileStep extends MavenStep {
  @Override
  void process(Context context) {
    echo(context, "compile application")

//    def jenkins = context.jenkins
//    jenkins.withDockerContainer(
//        "image": mavenImageName(context),
//        "args": mavenImageArgs(context)) {
//      jenkins.sh 'mvn -B clean compile'
//    }

    withMaven(context) {jenkins ->
      jenkins.sh 'mvn -B clean compile'
    }
  }
}
