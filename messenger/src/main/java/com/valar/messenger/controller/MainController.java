package com.valar.messenger.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/messenger")
@CrossOrigin(origins = {"http://localhost:3000"})
public class MainController {

    @GetMapping
    public String getAnswer() {
        log.info("get hello");
        return "Hello, front!";
    }

}
