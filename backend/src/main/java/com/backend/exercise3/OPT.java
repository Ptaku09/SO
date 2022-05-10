package com.backend.exercise3;

import java.util.Arrays;

public class OPT extends Algorithm {
    private int[] nextRequestIn;

    public OPT(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        super(virtualMemorySize, physicalMemorySize, testSequence);
        createNextRequestIn(physicalMemorySize);
    }

    @Override
    public Results run() {
        int errors = 0;

        for (int i = 0; i < testSequence.length; i++) {
            boolean isFound = false;

            for (int j = 0; j < physicalMemory.length; j++)
                if (physicalMemory[j] == testSequence[i]) {
                    isFound = true;
                    nextRequestIn[j] = findNextRequestIn(testSequence[i], i);
                    break;
                }

            if (!isFound) {
                errors++;
                updatePhysicalMemory(testSequence[i], i);
            }

            updateNextRequestIn();
        }

        return new Results("OPT", errors);
    }

    private void createNextRequestIn(int physicalMemorySize) {
        nextRequestIn = new int[physicalMemorySize];
        Arrays.fill(nextRequestIn, -1);
    }

    private void updatePhysicalMemory(int currentTestCase, int currentIndex) {
        int next = findNextRequestIn(currentTestCase, currentIndex);
        int maxNextRequestIn = 0;
        int maxNextIndex = 0;

        for (int i = 0; i < physicalMemory.length; i++) {
            if (physicalMemory[i] == -1 || nextRequestIn[i] == -1) {
                physicalMemory[i] = currentTestCase;
                nextRequestIn[i] = next;
                return;
            }

            if (nextRequestIn[i] > maxNextRequestIn) {
                maxNextRequestIn = nextRequestIn[i];
                maxNextIndex = i;
            }
        }

        physicalMemory[maxNextIndex] = currentTestCase;
        nextRequestIn[maxNextIndex] = next;
    }

    private int findNextRequestIn(int currentTestCase, int currentIndex) {
        int nextRequestIn = 1;

        for (int i = currentIndex + 1; i < testSequence.length; i++)
            if (testSequence[i] == currentTestCase)
                return nextRequestIn;
            else
                nextRequestIn++;

        return -1;
    }

    private void updateNextRequestIn() {
        for (int i = 0; i < nextRequestIn.length; i++)
            if (nextRequestIn[i] != -1)
                nextRequestIn[i]--;
    }
}
