package com.notebook40.jenkins.pipeline

import com.notebook40.jenkins.context.Context

class Pipelines {
  static def buildCommonPipeline(jenkins, Map pipelineParameters) {
    Context context = new Context(jenkins, pipelineParameters)

    new DefaultPipelineBuilder().build(context)
  }
}
