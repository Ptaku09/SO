package com.exercise1.backend;

import com.exercise1.backend.comparators.TimeOfAppearanceComparator;

import java.util.ArrayList;
import java.util.List;

public class Manager {
    private final List<Process> fcfsProcesses = new ArrayList<>();
    private final List<Process> srtfProcesses = new ArrayList<>();
    private final List<Process> rrProcesses = new ArrayList<>();

    public Manager(int amount, double alpha, double beta) {
        List<Process> processes = GenerateProcesses.generateProcesses(amount, alpha, beta);
        processes.sort(new TimeOfAppearanceComparator());

        for (Process pr : processes) {
            try {
                fcfsProcesses.add((Process) pr.clone());
                srtfProcesses.add((Process) pr.clone());
                rrProcesses.add((Process) pr.clone());
            } catch (Exception e) {
                System.out.println("Something went wrong!");
            }
        }
    }

    public AlgorithmInformation FCFS() {
        FCFS fcfs = new FCFS(fcfsProcesses);
        return fcfs.run();
    }

    public AlgorithmInformation SRTF() {
        SRTF srtf = new SRTF(srtfProcesses);
        return srtf.run();
    }

    public AlgorithmInformation RR(int singleExecutionTime) {
        RR rr = new RR(rrProcesses, singleExecutionTime);
        return rr.run();
    }
}
