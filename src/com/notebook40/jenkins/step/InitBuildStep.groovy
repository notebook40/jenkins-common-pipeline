package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

class InitBuildStep extends AbstractStep {
  @Override
  void process(Context context) {
    echo(context, "Init build")

    echo(context, "Load properties")
    loadProperties(context)

    printParameters(context)
  }

  private void loadProperties(Context context) {
    // Load properties
    String propertiesString = context.jenkins.libraryResource(
        "com/notebook40/jenkins/pipeline/defaultPipeline.properties"
    )
    Properties properties = new Properties()
    properties.load(new StringReader(propertiesString))

    properties.each {
      if (!context.pipelineParameters.containsKey(it.key)) {
        context.pipelineParameters[it.key] = it.value
      }
    }
  }

  private void printParameters(Context context) {
    def parameters = "PipelineBuilder parameters: "
    context.pipelineParameters.each {
      parameters += "  ${it.key}: ${it.value}\n"
    }
    echo(context, parameters)
  }
}
