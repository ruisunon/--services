package com.hou27.prev_project.dto.summary;

import lombok.Getter;

@Getter
public class SummaryReq {
    String content;

    public SummaryReq() {
    }
    public SummaryReq(String content) {
        this.content = content;
    }
}
