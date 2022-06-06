package com.backend.exercise5;

public class Process {
    private int timeToFinish;
    private int powerDemand;

    public Process(int timeToFinish, int powerDemand) {
        this.timeToFinish = timeToFinish;
        this.powerDemand = powerDemand;
    }

    public int getTimeToFinish() {
        return timeToFinish;
    }

    public void setTimeToFinish(int timeToFinish) {
        this.timeToFinish = timeToFinish;
    }

    public int getPowerDemand() {
        return powerDemand;
    }

    public void setPowerDemand(int powerDemand) {
        this.powerDemand = powerDemand;
    }
}
