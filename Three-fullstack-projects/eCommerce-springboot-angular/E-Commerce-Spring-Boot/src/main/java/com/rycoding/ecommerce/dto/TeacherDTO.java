package com.rycoding.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    private Long id;

    private String name;

    private String department;
    private String title;
    private Long userId;

    private String postedBy;

}
