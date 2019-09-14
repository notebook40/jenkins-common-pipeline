package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

abstract class AbstractStep implements Step {
  @Override
  void execute(Context context) {
    if (!preStep(context)) {
      return
    }

    process(context)

    postStep(context)
  }

  boolean preStep(Context context) {
    return true
  }

  abstract void process(Context context)

  void postStep(Context context) {
  }

  static void echo(Context context, message) {
    context.jenkins.echo message
  }

  static void printParameters(Context context) {
    def parameters = "PipelineBuilder parameters:\n"
    context.pipelineParameters.each {
      parameters += "  ${it.key}: ${it.value}\n"
    }
    echo(context, parameters)
  }
}
