package com.rycoding.ecommerce.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class GeneralResponse {

    private HttpStatus status;

    private String message;

    private Object data;
}
