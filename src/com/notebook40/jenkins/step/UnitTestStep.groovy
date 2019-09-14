package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class UnitTestStep extends MavenStep {
  @Override
  void process(Context context) {
    echo(context, 'unit test')

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

    if (context.pipelineParameterBooleanValue("skipUnitTest")) {
      echo(context, 'Unit test is skipped by pipeline parameters')
      return false
    }

    return true
  }
}
