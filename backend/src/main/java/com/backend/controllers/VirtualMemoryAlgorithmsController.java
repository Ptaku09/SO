package com.backend.controllers;

import com.backend.exercise3.Manager;
import com.backend.exercise3.Results;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class VirtualMemoryAlgorithmsController {
    @CrossOrigin
    @GetMapping("/exercise3")
    public List<Results> exercise3(@RequestParam(value = "physicalSize", defaultValue = "10") int physicalSize, @RequestParam(value = "virtualSize", defaultValue = "12") int virtualSize, @RequestParam(value = "sequenceSize", defaultValue = "10") int sequenceSize) {
        return new Manager(physicalSize, virtualSize, sequenceSize).runSimulation();
    }
}
