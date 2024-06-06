package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class demoController {

    @GetMapping("/hello")
    public String helloWorld(@RequestParam(value = "myName", defaultValue = "World") String name){
        return String.format("Hello %s",name);
    }
}
