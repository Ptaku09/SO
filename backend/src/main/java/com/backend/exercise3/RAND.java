package com.backend.exercise3;

import java.util.Random;

public class RAND extends Algorithm {
    private final Random random = new Random();

    public RAND(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        super(virtualMemorySize, physicalMemorySize, testSequence);
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
        }

        return new Results("RAND", errors);
    }

    private void updatePhysicalMemory(int currentTestCase) {
        for (int i = 0; i < physicalMemory.length; i++)
            if (physicalMemory[i] == -1) {
                physicalMemory[i] = currentTestCase;
                return;
            }

        physicalMemory[random.nextInt(0, physicalMemory.length)] = currentTestCase;
    }
}
