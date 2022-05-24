package com.example.themeparks.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;

@AllArgsConstructor @Data
public class ResponseAPI {
    private String message;
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private FieldError fieldError;

    public ResponseAPI(String message, Integer status) {
        this.message = message;
        this.status = status;
    }
}
