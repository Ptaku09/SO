package com.backend.exercise3;

import java.util.Arrays;

public class FCFS extends Algorithm {
    private int[] timeInPhysicalMemory;

    public FCFS(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        super(virtualMemorySize, physicalMemorySize, testSequence);
        createTimeInPhysicalMemory(physicalMemorySize);
    }

    @Override
    public Results run() {
        int errors = 0;

        for (int currentTestCase : testSequence) {
            boolean isFound = false;

            for (int j : physicalMemory)
                if (j == currentTestCase) {
                    isFound = true;
                    break;
                }

            if (!isFound) {
                errors++;
                updatePhysicalMemory(currentTestCase);
            }

            updateTimeInPhysicalMemory();
        }

        return new Results("FCFS", errors);
    }

    private void createTimeInPhysicalMemory(int physicalMemorySize) {
        timeInPhysicalMemory = new int[physicalMemorySize];
        Arrays.fill(timeInPhysicalMemory, 0);
    }

    private void updatePhysicalMemory(int currentTestCase) {
        int maxTime = 0;
        int maxIndex = 0;

        for (int i = 0; i < physicalMemory.length; i++) {
            if (physicalMemory[i] == -1 || timeInPhysicalMemory[i] == 0) {
                physicalMemory[i] = currentTestCase;
                return;
            }

            if (timeInPhysicalMemory[i] > maxTime) {
                maxTime = timeInPhysicalMemory[i];
                maxIndex = i;
            }
        }

        physicalMemory[maxIndex] = currentTestCase;
        timeInPhysicalMemory[maxIndex] = 0;
    }

    private void updateTimeInPhysicalMemory() {
        for (int i = 0; i < timeInPhysicalMemory.length; i++) {
            if (physicalMemory[i] != -1) {
                timeInPhysicalMemory[i]++;
            }
        }
    }
}
