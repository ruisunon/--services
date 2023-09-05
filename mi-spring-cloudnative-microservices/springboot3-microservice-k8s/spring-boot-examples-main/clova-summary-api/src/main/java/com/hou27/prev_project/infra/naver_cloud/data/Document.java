package com.hou27.prev_project.infra.naver_cloud.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Document {
  String title;
  String content;

  public Document(String title, String content) {
    this.title = title;
    this.content = content;
  }
}