package com.exercise1.backend;

import com.exercise1.backend.comparators.RemainingTimeComparator;

import java.util.ArrayList;

public class SRTF {
    private ArrayList<Process> processes;

    public SRTF(ArrayList<Process> processes) {
        this.processes = processes;
    }

    public AlgorithmInformation run() {
        ArrayList<Process> activeProcesses = new ArrayList<>();
        long totalProcesses = processes.size();
        long timeOfActivity = 0;
        double sumOfWaitingTime = 0;
        double sumOfRunningTime = 0;
        long numberOfSwitchingOperations = 0;
        double sumOfTimeToFirstExecution = 0;

        while (!processes.isEmpty() || !activeProcesses.isEmpty()) {
            ArrayList<Integer> recentlyAddedProcessesIds = new ArrayList<>();

            while (!processes.isEmpty() && processes.get(0).getTimeOfAppearance() == timeOfActivity) {
                activeProcesses.add(processes.get(0));
                recentlyAddedProcessesIds.add(processes.get(0).getId());
                activeProcesses.sort(new RemainingTimeComparator());

                processes.remove(0);
            }

            Process currentProcess = null;

            if (!activeProcesses.isEmpty()) {
                currentProcess = activeProcesses.get(0);

                if (recentlyAddedProcessesIds.contains(currentProcess.getId()))
                    numberOfSwitchingOperations++;
            }

            if (currentProcess != null) {
                if (!currentProcess.isActive()) {
                    currentProcess.setFirstActivationTime(timeOfActivity);
                }

                currentProcess.execute();
                currentProcess.updateCurrent();

                if (currentProcess.isFinished()) {
                    sumOfWaitingTime += currentProcess.getWaitingTime();
                    sumOfRunningTime += currentProcess.getTimeFromFirstExecution();
                    sumOfTimeToFirstExecution += currentProcess.getTimeToFirstExecution();
                    numberOfSwitchingOperations++;

                    activeProcesses.remove(0);
                }
            }

            Process finalCurrentProcess = currentProcess;
            activeProcesses.forEach(pr -> {
                if (!pr.equals(finalCurrentProcess))
                    pr.update();
            });
            timeOfActivity++;
        }

        return new AlgorithmInformation("Shortest Remaining Time First", sumOfWaitingTime / totalProcesses, sumOfRunningTime / totalProcesses, sumOfTimeToFirstExecution / totalProcesses, numberOfSwitchingOperations);
    }
}
