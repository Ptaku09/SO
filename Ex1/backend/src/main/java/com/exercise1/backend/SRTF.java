package com.exercise1.backend;

import com.exercise1.backend.comparators.RemainingTimeComparator;

import java.util.ArrayList;
import java.util.List;

public class SRTF {
    private List<Process> processes;

    public SRTF(List<Process> processes) {
        this.processes = processes;
    }

    public AlgorithmInformation run() {
        List<Process> alreadyAppearedProcesses = new ArrayList<>();
        double totalAmountOfProcesses = processes.size();
        double timeOfSystemActivity = 0;
        double sumOfTotalWaitingTime = 0;
        double sumOfRunningTime = 0;
        double numberOfSwitchingOperations = 0;
        double sumOfTimeToFirstExecution = 0;

        while (!processes.isEmpty() || !alreadyAppearedProcesses.isEmpty()) {
            List<Integer> recentlyAddedProcessesIds = new ArrayList<>();

            while (!processes.isEmpty() && processes.get(0).getTimeOfAppearance() == timeOfSystemActivity) {
                alreadyAppearedProcesses.add(processes.get(0));
                recentlyAddedProcessesIds.add(processes.get(0).getId());
                alreadyAppearedProcesses.sort(new RemainingTimeComparator());

                processes.remove(0);
            }

            Process currentlyExecutedProcess = null;

            if (!alreadyAppearedProcesses.isEmpty()) {
                currentlyExecutedProcess = alreadyAppearedProcesses.get(0);

                if (recentlyAddedProcessesIds.contains(currentlyExecutedProcess.getId()))
                    numberOfSwitchingOperations++;
            }

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

                    alreadyAppearedProcesses.remove(0);
                }
            }

            Process finalCurrentlyExecutedProcess = currentlyExecutedProcess;
            alreadyAppearedProcesses.forEach(pr -> {
                if (!pr.equals(finalCurrentlyExecutedProcess))
                    pr.update();
            });
            timeOfSystemActivity++;
        }

        return new AlgorithmInformation("Shortest Remaining Time First", sumOfTotalWaitingTime / totalAmountOfProcesses, sumOfRunningTime / totalAmountOfProcesses, sumOfTimeToFirstExecution / totalAmountOfProcesses, numberOfSwitchingOperations);
    }
}
