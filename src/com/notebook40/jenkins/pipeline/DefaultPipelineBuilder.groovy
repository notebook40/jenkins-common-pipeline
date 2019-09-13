package com.notebook40.jenkins.pipeline

import com.notebook40.jenkins.context.Context
import com.notebook40.jenkins.step.CompileStep
import com.notebook40.jenkins.step.InitBuildStep

class DefaultPipelineBuilder implements PipelineBuilder {
  @Override
  void build(Context context) {
    def jenkins = context.jenkins

    jenkins.node {
      jenkins.stage('init build') {
        new InitBuildStep().execute(context)
      }

      jenkins.stage('compile app') {
        new CompileStep().execute(context)
      }
    }
  }
}
