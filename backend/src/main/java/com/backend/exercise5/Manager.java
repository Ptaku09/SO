package com.backend.exercise5;

import java.util.*;

public class Manager {
    private final List<CPU> processors;
    private final Queue<Process> processesQueue1;
    private final Queue<Process> processesQueue2;
    private final Queue<Process> processesQueue3;
    private final int cpusAmount;
    private final int maxLoad;
    private final int minLoad;
    private final int maxTries;

    public Manager(int cpusAmount, int maxLoad, int minLoad, int maxTries) {
        this.processors = new ArrayList<>();
        this.processesQueue1 = new LinkedList<>();
        this.processesQueue2 = new LinkedList<>();
        this.processesQueue3 = new LinkedList<>();
        this.cpusAmount = cpusAmount;
        this.maxLoad = maxLoad;
        this.minLoad = minLoad;
        this.maxTries = maxTries;

        init();
    }

    public List<Results> runSimulation() {
        List<Results> results = new ArrayList<>();

        results.add(new Algorithm1("Algorithm I", processors, processesQueue1, maxLoad, maxTries).run());
        processors.forEach(CPU::reset);

        results.add(new Algorithm2("Algorithm II", processors, processesQueue2, maxLoad).run());
        processors.forEach(CPU::reset);

        results.add(new Algorithm3("Algorithm III", processors, processesQueue3, minLoad, maxLoad).run());
        processors.forEach(CPU::reset);

        return results;
    }

    private void init() {
        createProcessors();
        createProcesses();
        cloneProcesses();
    }

    private void createProcessors() {
        for (int i = 0; i < this.cpusAmount; i++)
            processors.add(new CPU());
    }

    private void createProcesses() {
        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
            int cpuNumber = generateProcessorNumber();
            int timeToFinish = random.nextInt(500, 1300);
            int powerDemand = random.nextInt(3, 20);
            processesQueue1.add(new Process(cpuNumber, timeToFinish, powerDemand));
        }
    }

    private int generateProcessorNumber() {
        Random random = new Random();

        // 50% chance to choose processor number from 0 to this.cpusAmount / 4
        // 30% chance to choose processor number from this.cpusAmount / 4 to this.cpusAmount / 2
        // 20% chance to choose processor number from this.cpusAmount / 2 to this.cpusAmount

        return random.nextInt(100) < 50 ? random.nextInt(this.cpusAmount / 4) : random.nextInt(this.cpusAmount / 4, this.cpusAmount);
    }

    private void cloneProcesses() {
        for (Process process : processesQueue1)
            try {
                processesQueue2.add(process.clone());
                processesQueue3.add(process.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
    }
}
