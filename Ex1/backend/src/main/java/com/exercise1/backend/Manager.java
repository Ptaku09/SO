package com.exercise1.backend;

import java.util.ArrayList;

public class Manager {
    private ArrayList<Process> processes;

    public Manager(int amount, double alpha, double beta) {
        processes = GenerateProcesses.generateProcesses(amount, alpha, beta);
    }

    public void FCFS() {
        FCFS fcfs = new FCFS(processes);
        System.out.println(fcfs.run());
    }
}
