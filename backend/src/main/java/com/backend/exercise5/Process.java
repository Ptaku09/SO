package com.backend.exercise5;

public class Process implements Cloneable {
    private final int cpuNumber;
    private final int powerDemand;
    private int timeToFinish;

    public Process(int cpuNumber, int timeToFinish, int powerDemand) {
        this.cpuNumber = cpuNumber;
        this.timeToFinish = timeToFinish;
        this.powerDemand = powerDemand;
    }

    public void update() {
        this.timeToFinish--;
    }

    public int getCpuNumber() {
        return cpuNumber;
    }

    public int getPowerDemand() {
        return powerDemand;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }

    @Override
    public Process clone() throws CloneNotSupportedException {
        return (Process) super.clone();
    }
}
