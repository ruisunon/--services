package com.hou27.prev_project.infra.naver_cloud.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Option {
  String language;
  String model;
  int tone;
  int summaryCount;

  public Option(String language, String model, int tone, int summaryCount) {
    this.language = language;
    this.model = model;
    this.tone = tone;
    this.summaryCount = summaryCount;
  }
}
