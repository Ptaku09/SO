package com.exercise1.backend;

public class Process {
    private int id;
    private int processLength;
    private int timeOfAppearance;
    private long waitingTime = 0;
    private long timeFromFirstExecution = 0;
    private int timeToFinish;
    private boolean isFinished = false;
    private boolean isActive = false;

    public Process(int id, int processLength, int timeOfAppearance) {
        this.id = id;
        this.processLength = processLength;
        this.timeToFinish = processLength;
        this.timeOfAppearance = timeOfAppearance;
    }

    public void execute() {
        isActive = true;
        timeToFinish -= 1;

        if (timeToFinish == 0) {
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

    public int getTimeToFinish() {
        return timeToFinish;
    }

    public int getTimeOfAppearance() {
        return timeOfAppearance;
    }

    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public String toString() {
        return String.format("Waiting time: %8d%nTime from first execution: %8d%nProcess time: %8d%nTime to finish: %8d%nTime of appearance: %8d%n%n", waitingTime, timeFromFirstExecution, processLength, timeToFinish, timeOfAppearance);
    }
}
