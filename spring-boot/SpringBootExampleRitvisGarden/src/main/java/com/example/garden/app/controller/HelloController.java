package com.example.garden.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String defaultPage() {
        return "Greetings from Spring Boot!";
    }

}
