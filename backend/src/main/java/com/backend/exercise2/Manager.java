package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Manager {
    private final int driveSize;
    private final int initialHeadPosition;
    private final List<Process> fcfsProcesses = new ArrayList<>();
    private final List<Process> sstfProcesses = new ArrayList<>();
    private final List<Process> scanProcesses = new ArrayList<>();
    private final List<Process> cscanProcesses = new ArrayList<>();
    private final List<Process> edfProcesses = new ArrayList<>();
    private final List<Process> fdscanProcesses = new ArrayList<>();

    public Manager(int amount, int driveSize, int initialHeadPosition, double realTimeChance) {
        this.driveSize = driveSize;
        this.initialHeadPosition = initialHeadPosition % driveSize;

        List<Process> processes = generateProcesses(amount, driveSize, realTimeChance % 101);
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

        results.add(new FCFS(driveSize, initialHeadPosition, fcfsProcesses).run());
        results.add(new SSTF(driveSize, initialHeadPosition, sstfProcesses).run());
        results.add(new SCAN(driveSize, initialHeadPosition, scanProcesses).run());
        results.add(new CSCAN(driveSize, initialHeadPosition, cscanProcesses).run());
        results.add(new EDF(driveSize, initialHeadPosition, edfProcesses).run());
        results.add(new FDSCAN(driveSize, initialHeadPosition, fdscanProcesses).run());
        return results;
    }

    private static List<Process> generateProcesses(int amount, int driveSize, double realTimeChance) {
        List<Process> processes = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < amount; i++)
            processes.add(new Process(random.nextInt(1, 1000), random.nextInt(1, driveSize), random.nextDouble(0, 100) <= realTimeChance, random.nextInt(1, driveSize / 3)));

        return processes;
    }
}
