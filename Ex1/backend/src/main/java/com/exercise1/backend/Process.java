package com.exercise1.backend;

public class Process {
    private int id;
    private int processLength;
    private int waitingTime = 0;
    private int timeToFinish;
    private int timeOfAppearance;

    public Process(int id, int processLength, int timeOfAppearance) {
        this.id = id;
        this.processLength = processLength;
        this.timeToFinish = processLength;
        this.timeOfAppearance = timeOfAppearance;
    }

    public int getId() {
        return id;
    }

    public int getProcessLength() {
        return processLength;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }

    public int getTimeOfAppearance() {
        return timeOfAppearance;
    }

    @Override
    public String toString() {
        return String.format("%8d %8d %10d %8d %8d%n", id, processLength, waitingTime, timeToFinish, timeOfAppearance);
    }
}
