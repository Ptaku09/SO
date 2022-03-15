package com.exercise1.backend;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SchedulingAlgorithmsController {
    @PostMapping("/exercise1")
    public List<AlgorithmInformation> exercise1(@RequestParam(value = "objects", defaultValue = "1000") int amountOfObjects, @RequestParam(value = "rrExecTime", defaultValue = "200") int rrExecTime, @RequestParam(value = "alpha", defaultValue = "1") double alpha, @RequestParam(value = "beta", defaultValue = "1") double beta) {
        List<AlgorithmInformation> algorithmInformation = new ArrayList<>();

        Manager manager = new Manager(amountOfObjects, alpha, beta);
        algorithmInformation.add(manager.FCFS());
        algorithmInformation.add(manager.SRTF());
        algorithmInformation.add(manager.RR(rrExecTime));

        return algorithmInformation;
    }
}
