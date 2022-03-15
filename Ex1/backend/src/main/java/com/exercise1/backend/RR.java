package com.exercise1.backend;

import java.util.ArrayList;

public class RR {
    private ArrayList<Process> processes;
    private int singleExecutionTime;

    public RR(ArrayList<Process> processes, int singleExecutionTime) {
        this.processes = processes;
        this.singleExecutionTime = singleExecutionTime;
    }

    public AlgorithmInformation run() {
        ArrayList<Process> activeProcesses = new ArrayList<>();
        long totalProcesses = processes.size();
        long timeOfActivity = 0;
        double sumOfWaitingTime = 0;
        double sumOfRunningTime = 0;
        long numberOfSwitchingOperations = 0;
        int processExecution = 0;
        int currentProcessId = 0;
        double sumOfTimeToFirstExecution = 0;

        while (!processes.isEmpty() || !activeProcesses.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getTimeOfAppearance() == timeOfActivity) {
                activeProcesses.add(processes.get(0));
                processes.remove(0);
            }

            Process currentProcess = null;

            if (currentProcessId >= activeProcesses.size())
                currentProcessId = 0;

            if (activeProcesses.size() > 0)
                currentProcess = activeProcesses.get(currentProcessId);

            if (currentProcess != null) {
                if (processExecution < singleExecutionTime) {
                    if (!currentProcess.isActive()) {
                        currentProcess.setFirstActivationTime(timeOfActivity);
                    }

                    currentProcess.execute();
                    currentProcess.updateCurrent();
                    processExecution++;


                } else {
                    processExecution = 0;
                    currentProcessId++;
                    numberOfSwitchingOperations++;
                }

                if (currentProcess.isFinished()) {
                    sumOfWaitingTime += currentProcess.getWaitingTime();
                    sumOfRunningTime += currentProcess.getTimeFromFirstExecution();
                    sumOfTimeToFirstExecution += currentProcess.getTimeToFirstExecution();
                    numberOfSwitchingOperations++;

                    activeProcesses.remove(currentProcessId);
                    processExecution = 0;
                }
            }

            Process finalCurrentProcess = currentProcess;
            activeProcesses.forEach(pr -> {
                if (!pr.equals(finalCurrentProcess))
                    pr.update();
            });
            timeOfActivity++;
        }

        return new AlgorithmInformation("Round Robin", sumOfWaitingTime / totalProcesses, sumOfRunningTime / totalProcesses, sumOfTimeToFirstExecution / totalProcesses, numberOfSwitchingOperations);
    }
}
