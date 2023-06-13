package io.endeavour.stocks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping(value = "/hey")
    public String hey(){return "Hey Ganesh";}
}
