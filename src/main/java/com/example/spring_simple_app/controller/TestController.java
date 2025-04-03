package com.example.spring_simple_app.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/spring")
    public Response test() {
        return Response.builder()
                .code("200")
                .message("helle from spring simple app")
                .build();
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String code;
        private String message;
    }
}
