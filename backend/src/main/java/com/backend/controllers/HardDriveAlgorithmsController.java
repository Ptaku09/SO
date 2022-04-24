package com.backend.controllers;

import com.backend.exercise2.Manager;
import com.backend.exercise2.Results;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HardDriveAlgorithmsController {
    @CrossOrigin
    @GetMapping("/exercise2")
    public List<Results> exercise2(@RequestParam(value = "amount", defaultValue = "100") int amount, @RequestParam(value = "driveCapacity", defaultValue = "100") int driveCapacity, @RequestParam(value = "initialPosition", defaultValue = "0") int initialPosition, @RequestParam(value = "realTimeChance", defaultValue = "30") int realTimeChance) {
        return new Manager(amount, driveCapacity, initialPosition, realTimeChance).runSimulation();
    }
}
