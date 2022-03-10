package com.exercise1.backend;

public class AlgorithmInformation {
    private double averageWaitingTime;
    private double averageRunningTime;
    private long longestWaitingTime;
    private long numberOfSwitchingOperations;

    public AlgorithmInformation(double averageWaitingTime, double averageRunningTime, long longestWaitingTime, long numberOfSwitchingOperations) {
        this.averageWaitingTime = averageWaitingTime;
        this.averageRunningTime = averageRunningTime;
        this.longestWaitingTime = longestWaitingTime;
        this.numberOfSwitchingOperations = numberOfSwitchingOperations;
    }

    @Override
    public String toString() {
        return String.format("Average waiting time: %10.2f%nAverage running time: %10.2f%nLongest waiting time: %8d%nNumber of switching operations: %8d", averageWaitingTime, averageRunningTime, longestWaitingTime, numberOfSwitchingOperations);
    }
}
