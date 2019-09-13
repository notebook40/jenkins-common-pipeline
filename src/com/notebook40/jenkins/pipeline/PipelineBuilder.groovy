package com.notebook40.jenkins.pipeline

import com.notebook40.jenkins.context.Context

interface PipelineBuilder {
  void build(Context context)
}