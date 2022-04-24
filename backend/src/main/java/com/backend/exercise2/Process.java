package com.backend.exercise2;

public class Process implements Cloneable {
    private final int initialDeadLine;
    private final int initialTime;
    private final int initialIndex;
    private final boolean realTime;
    private int deadLine;
    private double waitingTime = 0;

    public Process(int initialTime, int initialIndex, boolean realTime, int deadLine) {
        this.initialTime = initialTime;
        this.initialIndex = initialIndex;
        this.realTime = realTime;
        this.deadLine = deadLine;
        this.initialDeadLine = deadLine;
    }

    public void update() {
        this.waitingTime++;
    }

    public void updateRealTime() {
        this.waitingTime++;
        this.deadLine--;
    }

    public int getInitialDeadLine() {
        return initialDeadLine;
    }

    public int getInitialTime() {
        return initialTime;
    }

    public int getInitialIndex() {
        return initialIndex;
    }

    public boolean isRealTime() {
        return realTime;
    }

    public int getDeadLine() {
        return deadLine;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    @Override
    public Process clone() throws CloneNotSupportedException {
        return (Process) super.clone();
    }
}
