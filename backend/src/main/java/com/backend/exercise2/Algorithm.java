package com.backend.exercise2;

import java.util.List;

public abstract class Algorithm implements Runnable {
    protected final int driveSize;
    protected final int initialHeadPosition;
    protected final List<Process> processes;

    protected Algorithm(int driveSize, int initialHeadPosition, List<Process> processes) {
        this.driveSize = driveSize;
        this.initialHeadPosition = initialHeadPosition;
        this.processes = processes;
    }

    @Override
    public abstract Results run();
}
