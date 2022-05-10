package com.backend.exercise3;

public abstract class Algorithm implements Runnable {
    protected final int[] testSequence;
    protected final int virtualMemorySize;
    protected int[] physicalMemory;

    public Algorithm(int virtualMemorySize, int physicalMemorySize, int[] testSequence) {
        this.virtualMemorySize = virtualMemorySize;
        this.physicalMemory = new int[physicalMemorySize];
        this.testSequence = testSequence;
    }

    @Override
    public abstract Results run();
}
