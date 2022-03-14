package com.exercise1.backend;

public class AlgorithmInformation {
    private double averageWaitingTime;
    private double averageRunningTime;
    private double averageTimeToFirstExecution;
    private long numberOfSwitchingOperations;

    public AlgorithmInformation(double averageWaitingTime, double averageRunningTime, double averageTimeToFirstExecution, long numberOfSwitchingOperations) {
        this.averageWaitingTime = averageWaitingTime;
        this.averageRunningTime = averageRunningTime;
        this.averageTimeToFirstExecution = averageTimeToFirstExecution;
        this.numberOfSwitchingOperations = numberOfSwitchingOperations;
    }

    @Override
    public String toString() {
        return String.format("Average time to first execution: %10.2f%nAverage total waiting time: %10.2f%nAverage time from first execution to end: %10.2f%nNumber of switching operations: %8d", averageTimeToFirstExecution, averageWaitingTime, averageRunningTime, numberOfSwitchingOperations);
    }
}
