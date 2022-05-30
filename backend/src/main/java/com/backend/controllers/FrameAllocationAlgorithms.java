package com.backend.controllers;

import com.backend.exercise4.Manager;
import com.backend.exercise4.Results;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FrameAllocationAlgorithms {
    @CrossOrigin
    @GetMapping("/exercise4")
    public List<Results> exercise3(@RequestParam(value = "processes", defaultValue = "10") int numberOfProcesses, @RequestParam(value = "processLength", defaultValue = "1000") int testSequenceLengthPerProcess, @RequestParam(value = "frames", defaultValue = "50") int numberOfFrames, @RequestParam(value = "scuffleTime", defaultValue = "50") int scuffleTime, @RequestParam(value = "scufflePercent", defaultValue = "50") int scufflePercentToDetect) {
        return new Manager(numberOfProcesses, testSequenceLengthPerProcess, numberOfFrames, scuffleTime, scufflePercentToDetect).runSimulation();
    }
}
