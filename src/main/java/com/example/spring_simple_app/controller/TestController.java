package com.example.spring_simple_app.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    Environment environment;

    @GetMapping("/spring")
    public Response test() {
        return Response.builder()
                .code("200")
                .message("helle from spring simple app")
                .build();
    }

    @GetMapping("/env/{key}")
    public Response env(@PathVariable String key) {
        return Response.builder()
                .code("200")
                .message("env value for " + key + " is " + environment.getProperty(key, "default"))
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
