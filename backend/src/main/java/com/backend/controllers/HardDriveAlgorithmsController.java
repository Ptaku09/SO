package com.backend.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HardDriveAlgorithmsController {
    @CrossOrigin
    @GetMapping("/exercise2")
    public String exercise2() {
        return "test";
    }
}
