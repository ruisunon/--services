package com.hou27.prev_project.infra.naver_cloud.env;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NCloudKey {
  @Value("${naver_cloud.client.id}")
  private String client_id;

  @Value("${naver_cloud.client.secret}")
  private String client_secret;

  public String getClient_id() {
    return client_id;
  }

  public String getClient_secret() {
    return client_secret;
  }
}
