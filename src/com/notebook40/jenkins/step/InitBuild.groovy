package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class InitBuild extends AbstractStep {
  @Override
  void process(Context context) {
    echo(context, "Init build")

    printParameters(context)
  }

  private void printParameters(Context context) {
    def parameters = "PipelineBuilder parameters: "
    context.pipelineParameters.each{
      parameters += "  ${it.key}: ${it.value}\n"
    }
    echo(context, parameters)
  }
}
