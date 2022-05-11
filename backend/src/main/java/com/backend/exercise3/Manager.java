package com.backend.exercise3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager {
    private final static int[] BASE_TEST_SEQUENCE = new int[]{0, 1, 2, 3, 0, 1, 4, 2, 1, 0, 3, 4};
    private final static int BACKUP_VIRTUAL_MEM_SIZE = 40;
    private final static int BACKUP_RAM_SIZE = 8;
    private final int virtualMemorySize;
    private final int physicalMemorySize;
    private int[] testSequence;

    public Manager(int virtualMemorySize, int physicalMemorySize, int testSequenceLength) {
        int[] validatedData = validateData(virtualMemorySize, physicalMemorySize);
        this.virtualMemorySize = validatedData[0];
        this.physicalMemorySize = validatedData[1];
        generateTestSequence(testSequenceLength);
    }

    public List<Results> runSimulation() {
        List<Results> results = new ArrayList<>();

        results.add(new FCFS(virtualMemorySize, physicalMemorySize, testSequence).run());
        results.add(new OPT(virtualMemorySize, physicalMemorySize, testSequence).run());
        results.add(new LRU(virtualMemorySize, physicalMemorySize, testSequence).run());
        results.add(new ALRU(virtualMemorySize, physicalMemorySize, testSequence).run());
        results.add(new RAND(virtualMemorySize, physicalMemorySize, testSequence).run());

        return results;
    }

    private int[] validateData(int virtualMemorySize, int physicalMemorySize) {
        if (physicalMemorySize <= 0 || virtualMemorySize <= 0)
            return new int[]{BACKUP_VIRTUAL_MEM_SIZE, BACKUP_RAM_SIZE};
        else if (physicalMemorySize > virtualMemorySize)
            return new int[]{physicalMemorySize + 1, physicalMemorySize};

        return new int[]{virtualMemorySize, physicalMemorySize};
    }

    private void generateTestSequence(int testSequenceLength) {
        if (testSequenceLength < 0) testSequenceLength = 10000;

        this.testSequence = new int[testSequenceLength];
        Random random = new Random();
        int i = 0;

        while (i < testSequenceLength) {
            int amount = generateProcessSequenceLength(testSequenceLength);
            int[] subsequence = generateSubsequence();

            for (int j = 0; j < amount && i < testSequenceLength; j++) {
                int randomIndex = random.nextInt(0, physicalMemorySize);
                testSequence[i] = validateGeneratedNumber(subsequence, randomIndex, i != 0 ? testSequence[i - 1] : -1);
                i++;
            }
        }
    }

    private int generateProcessSequenceLength(int testSequenceLength) {
        Random random = new Random();

        if (testSequenceLength < 100)
            return random.nextInt((int) (testSequenceLength * 0.1), (int) (testSequenceLength * 0.3));
        else if (testSequenceLength < 1000)
            return random.nextInt((int) (testSequenceLength * 0.02), (int) (testSequenceLength * 0.08));
        else
            return random.nextInt((int) (testSequenceLength * 0.005), (int) (testSequenceLength * 0.02));
    }

    private int[] generateSubsequence() {
        int[] subsequence = new int[physicalMemorySize];
        Random random = new Random();

        for (int i = 0; i < physicalMemorySize; i++) {
            int number = random.nextInt(0, virtualMemorySize);
            subsequence[i] = validateGeneratedNumber(number, i != 0 ? subsequence[i - 1] : -1);
        }

        return subsequence;
    }

    private int validateGeneratedNumber(int number, int previousNumber) {
        return number == previousNumber ? (number + 1) % virtualMemorySize : number;
    }

    private int validateGeneratedNumber(int[] subsequence, int subsequenceIndex, int lastNumber) {
        return subsequence[subsequenceIndex] == lastNumber ? (subsequence[(subsequenceIndex + 1) % physicalMemorySize]) : subsequence[subsequenceIndex];
    }
}
