package com.backend.exercise3;

import java.util.Arrays;

public abstract class Algorithm implements Runnable {
    protected final int[] testSequence;
    protected final int virtualMemorySize;
    protected int[] physicalMemory;

    public Algorithm(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        this.virtualMemorySize = virtualMemorySize;
        this.testSequence = testSequence;
        generatePhysicalMemory(physicalMemorySize);
    }

    @Override
    public abstract Results run();

    private void generatePhysicalMemory(int physicalMemorySize) {
        this.physicalMemory = new int[physicalMemorySize];
        Arrays.fill(physicalMemory, -1);
    }
}
