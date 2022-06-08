package com.backend.exercise5;

import java.util.List;
import java.util.Queue;

public class Algorithm2 extends Algorithm {
    public Algorithm2(String algorithmName, List<CPU> processors, Queue<Process> processesQueue, int maxLoad) {
        super(algorithmName, processors, processesQueue, maxLoad);
    }

    @Override
    protected int findProcessor(int cpuNumber) {
        this.loadQuestions++;

        if (processors.get(cpuNumber).getLoad() < maxLoad)
            return cpuNumber;
        else {
            int temp = cpuNumber + 1;
            int randomProcessor = temp % processors.size();

            while (processors.get(randomProcessor).getLoad() >= maxLoad) {
                this.loadQuestions++;
                temp++;
                randomProcessor = temp % processors.size();
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
