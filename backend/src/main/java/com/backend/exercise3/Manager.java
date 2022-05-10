package com.backend.exercise3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Manager {
    private final static int[] BASE_TEST_SEQUENCE = new int[]{0, 1, 2, 3, 0, 1, 4, 0, 1, 2, 3, 4};
    private final static int BACKUP_VIRTUAL_MEM_SIZE = 12;
    private final static int BACKUP_RAM_SIZE = 10;
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

        results.add(new FCFS(virtualMemorySize, physicalMemorySize, BASE_TEST_SEQUENCE).run());
        results.add(new OPT(virtualMemorySize, physicalMemorySize, BASE_TEST_SEQUENCE).run());

        return results;
    }

    private int[] validateData(int virtualMemorySize, int physicalMemorySize) {
        if (physicalMemorySize <= 0 || virtualMemorySize <= 0)
            return new int[]{BACKUP_VIRTUAL_MEM_SIZE, BACKUP_RAM_SIZE};
        else if (physicalMemorySize > virtualMemorySize)
            return new int[]{virtualMemorySize + 1, physicalMemorySize};

        return new int[]{virtualMemorySize, physicalMemorySize};
    }

    private void generateTestSequence(int testSequenceLength) {
        Random random = new Random();
        this.testSequence = new int[testSequenceLength];

        for (int i = 0; i < testSequenceLength; i++) {
            int number = random.nextInt(0, virtualMemorySize);
            testSequence[i] = validateGeneratedNumber(number, i != 0 ? testSequence[i - 1] : -1);
        }
    }

    private int validateGeneratedNumber(int number, int lastNumber) {
        return number == lastNumber ? (number + 1) % virtualMemorySize : number;
    }
}
