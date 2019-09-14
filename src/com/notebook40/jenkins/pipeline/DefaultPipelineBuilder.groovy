package com.notebook40.jenkins.pipeline

import com.notebook40.jenkins.context.Context
import com.notebook40.jenkins.step.BuildImageStep
import com.notebook40.jenkins.step.CompileStep
import com.notebook40.jenkins.step.InitBuildStep
import com.notebook40.jenkins.step.IntegrationTestStep
import com.notebook40.jenkins.step.PackageStep
import com.notebook40.jenkins.step.UnitTestStep

class DefaultPipelineBuilder implements PipelineBuilder {
  @Override
  void build(Context context) {
    loadProperties(context)

    buildPipeline(context)
  }

  void loadProperties(Context context) {
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

  void buildPipeline(Context context) {
    def jenkins = context.jenkins

    jenkins.node {
      jenkins.stage('init build') {
        new InitBuildStep().execute(context)
      }

      jenkins.stage('compile app') {
        new CompileStep().execute(context)
      }

      jenkins.stage('unit test') {
        new UnitTestStep().execute(context)
      }

      jenkins.stage('integration test') {
        new IntegrationTestStep().execute(context)
      }

      jenkins.stage('package') {
        new PackageStep().execute(context)
      }

      jenkins.stage('build image') {
        new BuildImageStep().exec
        ute(context)
      }
    }
  }
}
