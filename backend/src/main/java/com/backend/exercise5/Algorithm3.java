package com.backend.exercise5;

import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Algorithm3 extends Algorithm {
    private final int minLoad;

    public Algorithm3(String algorithmName, List<CPU> processors, Queue<Process> processesQueue, int minLoad, int maxLoad) {
        super(algorithmName, processors, processesQueue, maxLoad);
        this.minLoad = minLoad;
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
        Random random = new Random();

        processors
                .stream()
                .filter(cpu -> cpu.getLoad() < minLoad)
                .forEach(cpu -> {
                    int asks = random.nextInt(1, 8);

                    for (int i = 0; i < asks; i++) {
                        int cpuNumber = random.nextInt(processors.size());

                        while (processors.get(cpuNumber).getLoad() > maxLoad && cpu.getLoad() < maxLoad)
                            cpu.addProcess(processors.get(cpuNumber).removeFirst());
                    }
                });
    }
}
