package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class CompileStep extends MavenStep {
  @Override
  void process(Context context) {
    echo(context, "maven image name: " + mavenImageName(context))
    echo(context, "maven image args: " + mavenImageArgs(context))
  }
}
