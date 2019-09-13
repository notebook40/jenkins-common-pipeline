package com.notebook40.jenkins.pipeline

import com.notebook40.jenkins.context.Context
import com.notebook40.jenkins.step.InitBuild

class DefaultPipelineBuilder implements PipelineBuilder {
  @Override
  void build(Context context) {
    def jenkins = context.jenkins

    jenkins.node {
      jenkins.stage('init build') {
        new InitBuild().execute(context)
      }
    }
  }
}
