package com.exercise1.backend;

import com.exercise1.backend.comparators.TimeOfAppearanceComparator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FCFS {
    private ArrayList<Process> processes;

    public FCFS(ArrayList<Process> processes) {
        processes.sort(new TimeOfAppearanceComparator());
        this.processes = processes;
    }

    public AlgorithmInformation run() {
        Queue<Process> activeProcesses = new LinkedList<>();
        long totalProcesses = processes.size();
        long timeOfActivity = 0;
        double sumOfWaitingTime = 0;
        double sumOfRunningTime = 0;
        long numberOfSwitchingOperations = 0;
        long longestWaitingTime = 0;

        while (processes.size() > 1 || !activeProcesses.isEmpty()) {
            while (processes.size() > 1 & processes.get(0).getTimeOfAppearance() == timeOfActivity) {
                activeProcesses.add(processes.get(0));
                processes.remove(0);
            }

            Process currentProcess = activeProcesses.peek();

            if (currentProcess != null) {
                currentProcess.execute();
                currentProcess.updateCurrent();

                if (currentProcess.isFinished()) {
                    sumOfWaitingTime += currentProcess.getWaitingTime();
                    sumOfRunningTime += currentProcess.getTimeFromFirstExecution();
                    numberOfSwitchingOperations++;

                    if (longestWaitingTime < currentProcess.getWaitingTime())
                        longestWaitingTime = currentProcess.getWaitingTime();

                    activeProcesses.remove();
                }
            }

            activeProcesses.forEach(pr -> {
                if (!pr.equals(currentProcess))
                    pr.update();
            });
            timeOfActivity++;
        }

        return new AlgorithmInformation(sumOfWaitingTime / totalProcesses, sumOfRunningTime / totalProcesses, longestWaitingTime, numberOfSwitchingOperations);
    }
}
