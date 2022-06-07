package com.backend.exercise5;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Algorithm1 extends Algorithm {
    private final int maxTries;

    public Algorithm1(String algorithmName, List<CPU> processors, Queue<Process> processesQueue, int maxLoad, int maxTries) {
        super(algorithmName, processors, processesQueue, maxLoad);
        this.maxTries = maxTries;
    }

    @Override
    protected int findProcessor(int cpuNumber) {
        Random random = new Random();

        for (int i = 0; i < this.maxTries; i++) {
            int processorNumber = random.nextInt(processors.size());
            this.loadQuestions++;

            if (processorNumber != cpuNumber && processors.get(processorNumber).getLoad() < maxLoad) {
                this.processMovements++;
                return processorNumber;
            }

            updateProcessors();
        }

        return cpuNumber;
    }

    @Override
    protected void giveHelp() {
    }
}
