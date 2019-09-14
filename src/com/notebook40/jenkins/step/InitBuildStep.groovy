package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class InitBuildStep extends AbstractStep {
  @Override
  void process(Context context) {
    echo(context, "Init build")

    printParameters(context)

    context.jenkins.checkout context.jenkins.scm
  }
}
