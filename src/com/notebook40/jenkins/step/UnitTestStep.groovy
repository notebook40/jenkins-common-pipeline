package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class UnitTestStep extends MavenStep {
  @Override
  void process(Context context) {
    withMaven(context) {
      try {
        context.jenkins.sh 'mvn test'
      } finally {
        context.jenkins.junit 'target/surefire-reports/TEST-*.xml'
      }
    }
  }

  @Override
  boolean preStep(Context context) {
    if (!super.preStep(context)) {
      return false
    }

    return !context.pipelineParameters["skipUnitTest"]
  }
}
