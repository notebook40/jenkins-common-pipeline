package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class IntegrationTestStep extends MavenStep {
  @Override
  void process(Context context) {
    // TODO to be tested.
    withMaven(context) {
      try {
        context.jenkins.sh 'maven integration-test'
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

    if (context.pipelineParameters["skipIntegrationTest"]) {
      echo(context, 'Integration test is skipped by pipeline parameters')
      return false
    }

    return true
  }
}
