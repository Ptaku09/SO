package com.exercise1.backend;

public class Process implements Cloneable {
    private int id;
    private int processLength;
    private int timeOfAppearance;
    private long waitingTime = 0;
    private long timeFromFirstExecution = 0;
    private int remainingTime;
    private boolean isFinished = false;
    private boolean isActive = false;

    public Process(int id, int processLength, int timeOfAppearance) {
        this.id = id;
        this.processLength = processLength;
        this.remainingTime = processLength;
        this.timeOfAppearance = timeOfAppearance;
    }

    public void execute() {
        isActive = true;
        remainingTime -= 1;

        if (remainingTime == 0) {
            isFinished = true;
        }
    }

    public void update() {
        waitingTime++;

        if (isActive)
            timeFromFirstExecution++;
    }

    public void updateCurrent() {
        timeFromFirstExecution++;
    }

    public int getId() {
        return id;
    }

    public int getProcessLength() {
        return processLength;
    }

    public long getWaitingTime() {
        return waitingTime;
    }

    public long getTimeFromFirstExecution() {
        return timeFromFirstExecution;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getTimeOfAppearance() {
        return timeOfAppearance;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return String.format("Waiting time: %8d%nTime from first execution: %8d%nProcess time: %8d%nTime to finish: %8d%nTime of appearance: %8d%n%n", waitingTime, timeFromFirstExecution, processLength, remainingTime, timeOfAppearance);
    }
}
