package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CSCAN extends Algorithm {
    public CSCAN(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        List<Process> queue = new ArrayList<>();
        double amountOfProcesses = processes.size();
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int displacements = 0;
        int currentHeadPosition = 0;
        Process currentProcess = null;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                queue.add(processes.remove(0));
            }

            int finalCurrentHeadPosition = currentHeadPosition;
            queue.sort(Comparator.comparingInt(p -> p.getInitialIndex() >= finalCurrentHeadPosition ? p.getInitialIndex() - finalCurrentHeadPosition : driveSize + 1));

            if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.get(0);
            }

            while (currentProcess != null && currentProcess.getInitialIndex() == currentHeadPosition) {
                queue.remove(0);
                sumOfWaitingTime += currentProcess.getWaitingTime();
                currentProcess = (queue.isEmpty() ? null : queue.get(0));
            }

            currentHeadPosition = (currentHeadPosition + 1) % driveSize;

            if (currentHeadPosition == 0)
                displacements++;

            way++;
            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("C-SCAN", way, displacements, -1, -1, sumOfWaitingTime / amountOfProcesses);
    }
}
