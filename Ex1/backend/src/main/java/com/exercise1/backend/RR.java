package com.exercise1.backend;

import java.util.ArrayList;
import java.util.List;

public class RR {
    private List<Process> processes;
    private final int singleExecutionTime;

    public RR(List<Process> processes, int singleExecutionTime) {
        this.processes = processes;
        this.singleExecutionTime = singleExecutionTime;
    }

    public AlgorithmInformation run() {
        List<Process> alreadyAppearedProcesses = new ArrayList<>();
        double totalAmountOfProcesses = processes.size();
        int timeOfRunningInCurrentScope = 0;
        int currentlyExecutedProcessPosition = 0;
        double timeOfSystemActivity = 0;
        double sumOfTotalWaitingTime = 0;
        double sumOfRunningTime = 0;
        double sumOfTimeToFirstExecution = 0;
        double numberOfSwitchingOperations = 0;

        while (!processes.isEmpty() || !alreadyAppearedProcesses.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getTimeOfAppearance() == timeOfSystemActivity) {
                alreadyAppearedProcesses.add(processes.get(0));
                processes.remove(0);
            }

            Process currentlyExecutedProcess = null;

            if (currentlyExecutedProcessPosition >= alreadyAppearedProcesses.size())
                currentlyExecutedProcessPosition = 0;

            if (alreadyAppearedProcesses.size() > 0)
                currentlyExecutedProcess = alreadyAppearedProcesses.get(currentlyExecutedProcessPosition);

            if (currentlyExecutedProcess != null) {
                if (timeOfRunningInCurrentScope < singleExecutionTime) {
                    if (!currentlyExecutedProcess.isActive())
                        currentlyExecutedProcess.setFirstActivationTime(timeOfSystemActivity);

                    currentlyExecutedProcess.execute();
                    currentlyExecutedProcess.updateCurrent();
                    timeOfRunningInCurrentScope++;
                } else {
                    timeOfRunningInCurrentScope = 0;
                    currentlyExecutedProcessPosition++;
                    numberOfSwitchingOperations++;
                }

                if (currentlyExecutedProcess.isFinished()) {
                    sumOfTotalWaitingTime += currentlyExecutedProcess.getTotalWaitingTime();
                    sumOfRunningTime += currentlyExecutedProcess.getTimeFromFirstExecution();
                    sumOfTimeToFirstExecution += currentlyExecutedProcess.getTimeToFirstExecution();
                    numberOfSwitchingOperations++;

                    alreadyAppearedProcesses.remove(currentlyExecutedProcessPosition);
                    timeOfRunningInCurrentScope = 0;
                }
            }

            Process finalCurrentlyExecutedProcess = currentlyExecutedProcess;
            alreadyAppearedProcesses.forEach(pr -> {
                if (!pr.equals(finalCurrentlyExecutedProcess))
                    pr.update();
            });
            timeOfSystemActivity++;
        }

        return new AlgorithmInformation("Round Robin", sumOfTotalWaitingTime / totalAmountOfProcesses, sumOfRunningTime / totalAmountOfProcesses, sumOfTimeToFirstExecution / totalAmountOfProcesses, numberOfSwitchingOperations);
    }
}
