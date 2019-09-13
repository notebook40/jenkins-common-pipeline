package com.notebook40.jenkins.step

import com.notebook40.jenkins.context.Context

interface Step extends Serializable {
  void execute(Context context)
}