package com.hou27.prev_project.dto.summary;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SummaryRes {
    String summary;

    public SummaryRes(String summary) {
        this.summary = summary;
    }

}
