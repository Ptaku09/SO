package com.backend.exercise1;

public class Process implements Cloneable {
    private final int id;
    private final int processLength;
    private final int timeOfAppearanceInSystem;
    private double totalWaitingTime = 0;
    private double timeFromFirstExecution = 0;
    private double timeToFirstExecution = 0;
    private int remainingTime;
    private boolean isFinished = false;
    private boolean isActive = false;
    private double firstActivationTime;

    public Process(int id, int processLength, int timeOfAppearanceInSystem) {
        this.id = id;
        this.processLength = processLength;
        this.remainingTime = processLength;
        this.timeOfAppearanceInSystem = timeOfAppearanceInSystem;
    }

    public void execute() {
        isActive = true;
        remainingTime -= 1;

        if (remainingTime == 0) {
            isFinished = true;
        }
    }

    public void update() {
        totalWaitingTime++;

        if (isActive)
            timeFromFirstExecution++;
        else
            timeToFirstExecution++;
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

    public double getTotalWaitingTime() {
        return totalWaitingTime;
    }

    public double getTimeFromFirstExecution() {
        return timeFromFirstExecution;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public int getTimeOfAppearance() {
        return timeOfAppearanceInSystem;
    }

    public boolean isActive() {
        return isActive;
    }

    public double getFirstActivationTime() {
        return firstActivationTime;
    }

    public double getTimeToFirstExecution() {
        return timeToFirstExecution;
    }

    public void setFirstActivationTime(double firstActivationTime) {
        this.firstActivationTime = firstActivationTime;
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
        return String.format("Waiting time: %8d%nTime from first execution: %8d%nProcess time: %8d%nTime to finish: %8d%nTime of appearance: %8d%n%n", totalWaitingTime, timeFromFirstExecution, processLength, remainingTime, timeOfAppearanceInSystem);
    }
}
