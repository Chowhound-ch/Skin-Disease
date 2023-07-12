package edu.hfut.innovate.community.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Value("${test}")
    private String test;

    @RequestMapping("/test")
    public String test() {

        return "test" + test;
    }
}
