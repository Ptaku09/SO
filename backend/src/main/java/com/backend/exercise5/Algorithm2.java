package com.backend.exercise5;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Algorithm2 extends Algorithm {
    public Algorithm2(String algorithmName, List<CPU> processors, Queue<Process> processesQueue, int maxLoad) {
        super(algorithmName, processors, processesQueue, maxLoad);
    }

    @Override
    protected int findProcessor(int cpuNumber) {
        Random random = new Random();
        this.loadQuestions++;

        if (processors.get(cpuNumber).getLoad() < maxLoad)
            return cpuNumber;
        else {
            int randomProcessor = random.nextInt(processors.size());

            while (processors.get(randomProcessor).getLoad() >= maxLoad) {
                this.loadQuestions++;
                randomProcessor = random.nextInt(processors.size());
                updateProcessors();
            }

            this.loadQuestions++;
            this.processMovements++;

            return randomProcessor;
        }
    }

    @Override
    protected void giveHelp() {
    }
}
