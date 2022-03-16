package com.exercise1.backend;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS {
    private List<Process> processes;

    public FCFS(List<Process> processes) {
        this.processes = processes;
    }

    public AlgorithmInformation run() {
        Queue<Process> alreadyAppearedProcesses = new LinkedList<>();
        double totalAmountOfProcesses = processes.size();
        double timeOfSystemActivity = 0;
        double sumOfTotalWaitingTime = 0;
        double sumOfRunningTime = 0;
        double numberOfSwitchingOperations = 0;
        double sumOfTimeToFirstExecution = 0;

        while (!processes.isEmpty() || !alreadyAppearedProcesses.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getTimeOfAppearance() == timeOfSystemActivity) {
                alreadyAppearedProcesses.add(processes.get(0));
                processes.remove(0);
            }

            Process currentlyExecutedProcess = alreadyAppearedProcesses.peek();

            if (currentlyExecutedProcess != null) {
                if (!currentlyExecutedProcess.isActive())
                    currentlyExecutedProcess.setFirstActivationTime(timeOfSystemActivity);

                currentlyExecutedProcess.execute();
                currentlyExecutedProcess.updateCurrent();

                if (currentlyExecutedProcess.isFinished()) {
                    sumOfTotalWaitingTime += currentlyExecutedProcess.getTotalWaitingTime();
                    sumOfRunningTime += currentlyExecutedProcess.getTimeFromFirstExecution();
                    sumOfTimeToFirstExecution += currentlyExecutedProcess.getTimeToFirstExecution();
                    numberOfSwitchingOperations++;

                    alreadyAppearedProcesses.remove();
                }
            }

            alreadyAppearedProcesses.forEach(pr -> {
                if (!pr.equals(currentlyExecutedProcess))
                    pr.update();
            });
            timeOfSystemActivity++;
        }

        return new AlgorithmInformation("First Come First Serve", sumOfTotalWaitingTime / totalAmountOfProcesses, sumOfRunningTime / totalAmountOfProcesses, sumOfTimeToFirstExecution / totalAmountOfProcesses, numberOfSwitchingOperations);
    }
}
