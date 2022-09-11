package com.helloword.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class ReadPropExample {

    @Value("${gMapReportUrl:MyURL}")
    private String gMapReportUrl;

    @RequestMapping("/springPropertyReader")
    String home() {
        return "Spring boot Hello World property reader!"+gMapReportUrl;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ReadPropExample.class, args);
    }

}

