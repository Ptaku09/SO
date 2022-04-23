package com.backend.exercise2;

public class Results {
    private String algorithmName;
    private double totalWay;
    private int displacements;
    private int unfinishedRealTimeProcesses;
    private int finishedRealTimeProcesses;
    private double averageWaitingTime;

    public Results(String algorithmName, double totalWay, int displacements, int unfinishedRealTimeProcesses, int finishedRealTimeProcesses, double averageWaitingTime) {
        this.algorithmName = algorithmName;
        this.totalWay = totalWay;
        this.displacements = displacements;
        this.unfinishedRealTimeProcesses = unfinishedRealTimeProcesses;
        this.finishedRealTimeProcesses = finishedRealTimeProcesses;
        this.averageWaitingTime = averageWaitingTime;
    }
}
