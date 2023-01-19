package io.endeavour.stocks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping(value = "/hello")
    public String hello(){
        return "Hello World";
    }
    @GetMapping(value = "/hello/World")
    public String helloWorld(){
        return "Hello World Again";
    }
}
