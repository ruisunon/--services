package com.hou27.prev_project.infra.naver_cloud.data;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClovaSummaryReq {
    Document document;
    Option option;

    public ClovaSummaryReq(Document document, Option option) {
        this.document = document;
        this.option = option;
    }
}