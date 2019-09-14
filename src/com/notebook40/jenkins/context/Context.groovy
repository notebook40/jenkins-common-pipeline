package com.notebook40.jenkins.context

class Context implements Serializable {
  def jenkins
  Map pipelineParameters

  Context(jenkins, Map pipelineParameters) {
    this.jenkins = jenkins
    this.pipelineParameters = pipelineParameters
  }

  def getJenkins() {
    return jenkins
  }

  Map getPipelineParameters() {
    return pipelineParameters
  }

  boolean pipelineParameterBooleanValue(String parameter) {
    return Boolean.valueOf((String)this.pipelineParameters[parameter])
  }
}
