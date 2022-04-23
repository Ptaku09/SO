package com.backend.exercise2;

public class Process implements Cloneable {
    private int initialTime;
    private int initialIndex;
    private boolean realTime;
    private int deadLine;
    private double waitingTime = 0;

    public Process(int initialTime, int initialIndex, boolean realTime, int deadLine) {
        this.initialTime = initialTime;
        this.initialIndex = initialIndex;
        this.realTime = realTime;
        this.deadLine = deadLine;
    }

    public void update() {
        this.waitingTime++;
    }

    public int getInitialTime() {
        return initialTime;
    }

    public void setInitialTime(int initialTime) {
        this.initialTime = initialTime;
    }

    public int getInitialIndex() {
        return initialIndex;
    }

    public void setInitialIndex(int initialIndex) {
        this.initialIndex = initialIndex;
    }

    public boolean isRealTime() {
        return realTime;
    }

    public void setRealTime(boolean realTime) {
        this.realTime = realTime;
    }

    public int getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(int deadLine) {
        this.deadLine = deadLine;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    @Override
    public Process clone() throws CloneNotSupportedException {
        return (Process) super.clone();
    }
}
