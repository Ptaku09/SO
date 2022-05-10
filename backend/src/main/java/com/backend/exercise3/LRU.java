package com.backend.exercise3;

import java.util.Arrays;

public class LRU extends Algorithm {
    private int[] recentUse;

    public LRU(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        super(virtualMemorySize, physicalMemorySize, testSequence);
        createRecentUseArray(physicalMemorySize);
    }

    @Override
    public Results run() {
        int errors = 0;

        for (int currentTestCase : testSequence) {
            boolean isFound = false;

            for (int j = 0; j < physicalMemory.length; j++)
                if (physicalMemory[j] == currentTestCase) {
                    isFound = true;
                    recentUse[j] = 0;
                    break;
                }

            if (!isFound) {
                errors++;
                updatePhysicalMemory(currentTestCase);
            }

            updateRecentUse();
        }

        return new Results("LRU", errors);
    }

    public void createRecentUseArray(int physicalMemorySize) {
        recentUse = new int[physicalMemorySize];
        Arrays.fill(recentUse, -1);
    }

    private void updatePhysicalMemory(int currentTestCase) {
        int maxRecentUse = 0;
        int maxRecentUseIndex = 0;

        for (int i = 0; i < physicalMemory.length; i++) {
            if (physicalMemory[i] == -1 || recentUse[i] == -1) {
                physicalMemory[i] = currentTestCase;
                recentUse[i] = 0;
                return;
            }

            if (recentUse[i] > maxRecentUse) {
                maxRecentUse = recentUse[i];
                maxRecentUseIndex = i;
            }
        }

        physicalMemory[maxRecentUseIndex] = currentTestCase;
        recentUse[maxRecentUseIndex] = 0;
    }

    private void updateRecentUse() {
        for (int i = 0; i < recentUse.length; i++)
            if (recentUse[i] != -1)
                recentUse[i]++;
    }
}
