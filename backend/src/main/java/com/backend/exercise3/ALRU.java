package com.backend.exercise3;

import java.util.LinkedList;
import java.util.Queue;

public class ALRU extends Algorithm {
    private final Queue<Integer> physicalMemoryQueue = new LinkedList<>();
    private final Queue<Integer> bitsQueue = new LinkedList<>();

    public ALRU(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        super(virtualMemorySize, physicalMemorySize, testSequence);
    }

    @Override
    public Results run() {
        int errors = 0;

        for (int currentTestCase : testSequence) {
            boolean isFound = false;

            for (int j : physicalMemoryQueue)
                if (j == currentTestCase) {
                    isFound = true;
                    break;
                }

            if (!isFound) {
                errors++;
                updatePhysicalMemoryQueue(currentTestCase);
            }
        }

        return new Results("Approximated LRU", errors);
    }

    private void updatePhysicalMemoryQueue(int currentTestCase) {
        if (physicalMemoryQueue.size() < physicalMemory.length) {
            bitsQueue.add(1);
            physicalMemoryQueue.add(currentTestCase);
            return;
        }

        while (!bitsQueue.isEmpty() && bitsQueue.peek() != 0) {
            bitsQueue.remove();
            bitsQueue.add(0);
            physicalMemoryQueue.add(physicalMemoryQueue.poll());
        }

        bitsQueue.remove();
        bitsQueue.add(1);
        physicalMemoryQueue.remove();
        physicalMemoryQueue.add(currentTestCase);
    }
}
