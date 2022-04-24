package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Manager {
    private final int driveCapacity;
    private final int initialHeadPosition;
    private final List<Process> fcfsProcesses = new ArrayList<>();
    private final List<Process> sstfProcesses = new ArrayList<>();
    private final List<Process> scanProcesses = new ArrayList<>();
    private final List<Process> cscanProcesses = new ArrayList<>();
    private final List<Process> edfProcesses = new ArrayList<>();
    private final List<Process> fdscanProcesses = new ArrayList<>();

    public Manager(int amount, int driveCapacity, int initialHeadPosition, int realTimeChance) {
        this.driveCapacity = driveCapacity;
        this.initialHeadPosition = initialHeadPosition % driveCapacity;

        List<Process> processes = generateProcesses(amount, driveCapacity, realTimeChance % 101);
        processes.sort(Comparator.comparing(Process::getInitialTime));

        for (Process process : processes)
            try {
                fcfsProcesses.add(process.clone());
                sstfProcesses.add(process.clone());
                scanProcesses.add(process.clone());
                cscanProcesses.add(process.clone());
                edfProcesses.add(process.clone());
                fdscanProcesses.add(process.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
    }

    public List<Results> runSimulation() {
        List<Results> results = new ArrayList<>();

        results.add(new FCFS(driveCapacity, initialHeadPosition, fcfsProcesses).run());
        results.add(new SSTF(driveCapacity, initialHeadPosition, sstfProcesses).run());
        results.add(new SCAN(driveCapacity, initialHeadPosition, scanProcesses).run());
        results.add(new CSCAN(driveCapacity, initialHeadPosition, cscanProcesses).run());
        results.add(new EDF(driveCapacity, initialHeadPosition, edfProcesses).run());
        results.add(new FDSCAN(driveCapacity, initialHeadPosition, fdscanProcesses).run());
        return results;
    }

    private static List<Process> generateProcesses(int amount, int driveCapacity, double realTimeChance) {
        List<Process> processes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < amount; i++)
            processes.add(new Process(random.nextInt(1, 1000), random.nextInt(1, driveCapacity), random.nextDouble(0, 100) <= realTimeChance, random.nextInt(1, driveCapacity / 3)));

        return processes;
    }
}
