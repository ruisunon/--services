package com.hou27.prev_project.infra.naver_cloud;

import com.hou27.prev_project.dto.summary.SummaryRes;
import java.io.IOException;

public interface ClovaService {

  // 요약할 텍스트를 입력받아 요약된 텍스트를 반환
  SummaryRes getSummary(String content) throws IOException;

}
