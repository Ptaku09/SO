package com.backend.exercise2;

public record Results(String algorithmName,
                      double totalWay,
                      int displacements,
                      int unfinishedRealTimeProcesses,
                      int finishedRealTimeProcesses,
                      double averageWaitingTime) {
}
