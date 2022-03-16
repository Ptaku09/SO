package com.exercise1.backend;

public class AlgorithmInformation {
    private String algorithmName;
    private double averageWaitingTime;
    private double averageRunningTime;
    private double averageTimeToFirstExecution;
    private double numberOfSwitchingOperations;

    public AlgorithmInformation(String algorithmName, double averageWaitingTime, double averageRunningTime, double averageTimeToFirstExecution, double numberOfSwitchingOperations) {
        this.algorithmName = algorithmName;
        this.averageWaitingTime = averageWaitingTime;
        this.averageRunningTime = averageRunningTime;
        this.averageTimeToFirstExecution = averageTimeToFirstExecution;
        this.numberOfSwitchingOperations = numberOfSwitchingOperations;
    }

    public String getAlgorithmName() {
        return algorithmName;
    }

    public double getAverageWaitingTime() {
        return averageWaitingTime;
    }

    public double getAverageRunningTime() {
        return averageRunningTime;
    }

    public double getAverageTimeToFirstExecution() {
        return averageTimeToFirstExecution;
    }

    public double getNumberOfSwitchingOperations() {
        return numberOfSwitchingOperations;
    }

    @Override
    public String toString() {
        return String.format("Average time to first execution: %10.2f%nAverage total waiting time: %10.2f%nAverage time from first execution to end: %10.2f%nNumber of switching operations: %8d", averageTimeToFirstExecution, averageWaitingTime, averageRunningTime, numberOfSwitchingOperations);
    }
}
