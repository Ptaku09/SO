package com.backend.exercise4;

public record Results(String algorithmName, int totalErrors, int[] errorsPerProcess, int scuffleErrors, int stoppedProcesses) {
}
