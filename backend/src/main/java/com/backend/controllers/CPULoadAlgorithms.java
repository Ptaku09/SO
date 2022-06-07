package com.backend.controllers;

import com.backend.exercise5.Manager;
import com.backend.exercise5.Results;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CPULoadAlgorithms {
    @CrossOrigin
    @GetMapping("/exercise5")
    public List<Results> exercise5(@RequestParam(value = "cpusAmount", defaultValue = "50") int cpusAmount, @RequestParam(value = "maxLoad", defaultValue = "80") int maxLoad, @RequestParam(value = "minLoad", defaultValue = "30") int minLoad, @RequestParam(value = "tries", defaultValue = "15") int tries) {
        return new Manager(cpusAmount, maxLoad, minLoad, tries).runSimulation();
    }
}
