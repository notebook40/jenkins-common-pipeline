import com.notebook40.jenkins.pipeline.Pipelines

def call(Map pipelineParameters) {
  Pipelines.buildCommonPipeline(this, pipelineParameters)
}
