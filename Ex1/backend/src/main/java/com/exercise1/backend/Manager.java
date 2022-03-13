package com.exercise1.backend;

import com.exercise1.backend.comparators.TimeOfAppearanceComparator;

import java.util.ArrayList;

class Test {
    public static void main(String[] args) {
        Manager man = new Manager(5000, 1, 4);
        man.FCFS();
    }
}

public class Manager {
    private ArrayList<Process> processes;
    private ArrayList<Process> fcfsProcesses = new ArrayList<>();
    private ArrayList<Process> srtfProcesses = new ArrayList<>();
    private ArrayList<Process> rrProcesses = new ArrayList<>();

    public Manager(int amount, double alpha, double beta) {
        processes = GenerateProcesses.generateProcesses(amount, alpha, beta);
        processes.sort(new TimeOfAppearanceComparator());

        for (Process pr : processes) {
            try {
                fcfsProcesses.add((Process) pr.clone());
                srtfProcesses.add((Process) pr.clone());
                rrProcesses.add((Process) pr.clone());
            } catch (Exception e) {
                System.out.println("sth");
            }
        }
    }

    public void FCFS() {
        FCFS fcfs = new FCFS(fcfsProcesses);
        SRTF srtf = new SRTF(srtfProcesses);
//        System.out.println(fcfs.run());
        System.out.println();
        System.out.println(srtf.run());
    }
}
