package com.hou27.prev_project.controller;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hou27.prev_project.dto.summary.SummaryReq;
import com.hou27.prev_project.dto.summary.SummaryRes;
import com.hou27.prev_project.infra.naver_cloud.ClovaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;

@RequiredArgsConstructor
@RequestMapping("/test")
@Controller
public class TestController {
  private final ClovaService clovaService;

    @PostMapping("/summarize")
    public ResponseEntity<SummaryRes> summary_test(@RequestBody SummaryReq summaryReq) throws IOException {
      SummaryRes res = clovaService.getSummary(summaryReq.getContent());
      return new ResponseEntity<>(res, HttpStatus.OK);
    }

}
