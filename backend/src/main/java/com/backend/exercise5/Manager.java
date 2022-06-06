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
        return null;
    }

    private void init() {
        createCpus();
        createProcesses();
        cloneProcesses();
    }

    private void createCpus() {
        for (int i = 0; i < this.cpusAmount; i++)
            processors.add(new CPU());
    }

    private void createProcesses() {
        Random random = new Random();

        for (int i = 0; i < 1000; i++) {
            int cpuNumber = random.nextInt(this.cpusAmount);
            int timeToFinish = random.nextInt(5, 50);
            int powerDemand = random.nextInt(1, 10);
            processesQueue1.add(new Process(cpuNumber, timeToFinish, powerDemand));
        }
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
