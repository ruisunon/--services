package com.hou27.prev_project.infra.naver_cloud;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.hou27.prev_project.dto.summary.SummaryRes;
import com.hou27.prev_project.infra.naver_cloud.data.ClovaSummaryReq;
import com.hou27.prev_project.infra.naver_cloud.data.Document;
import com.hou27.prev_project.infra.naver_cloud.data.Option;
import com.hou27.prev_project.infra.naver_cloud.env.NCloudKey;

@Service
public class ClovaServiceImpl implements ClovaService {
  private NCloudKey nCloudKey;
  public ClovaServiceImpl(NCloudKey nCloudKey) {
    this.nCloudKey = nCloudKey;
  }

  // 텍스트를 입력받아 요약된 텍스트를 반환
  public SummaryRes getSummary(String content) throws HttpClientErrorException {
    String api_url = "https://naveropenapi.apigw.ntruss.com/text-summary/v1/summarize";
    String client_id = nCloudKey.getClient_id();
    String client_secret = nCloudKey.getClient_secret();

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-NCP-APIGW-API-KEY-ID", client_id);
    headers.add("X-NCP-APIGW-API-KEY", client_secret);
    headers.setContentType(MediaType.APPLICATION_JSON);

    ClovaSummaryReq req = new ClovaSummaryReq(
        new Document(null, content),
        new Option("ko", "general", 0, 3)
    );

    HttpEntity<ClovaSummaryReq> entity = new HttpEntity<>(req, headers);

    RestTemplate restTemplate = new RestTemplate();

    try {
//    String result = restTemplate.postForObject(api_url, entity, String.class);
      return restTemplate.exchange(api_url, org.springframework.http.HttpMethod.POST, entity, SummaryRes.class).getBody();
    } catch (HttpClientErrorException e) {
      return null;
    }
  }

}