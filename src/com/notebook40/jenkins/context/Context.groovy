package com.notebook40.jenkins.context

class Context implements Serializable {
  def jenkins
  def pipelineParameters

  Context(jenkins, pipelineParameters) {
    this.jenkins = jenkins
    this.pipelineParameters = pipelineParameters
  }

  def getJenkins() {
    return jenkins
  }

  def getPipelineParameters() {
    return pipelineParameters
  }
}
