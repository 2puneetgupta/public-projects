package com.assesment.rest.app.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {

    @GetMapping("/")
    public String defaultPage() {
        return "Youhave reached default Page. Please use correct URL.";
    }

}
