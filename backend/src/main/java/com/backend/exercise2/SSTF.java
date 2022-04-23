package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SSTF extends Algorithm {
    public SSTF(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        List<Process> queue = new ArrayList<>();
        double amountOfProcesses = processes.size();
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int currentHeadPosition = initialHeadPosition;
        Process currentProcess = null;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                queue.add(processes.remove(0));

                if (queue.size() > 1) {
                    int finalCurrentHeadPosition = currentHeadPosition;
                    queue.subList(1, queue.size()).sort(Comparator.comparingInt(p -> Math.abs(p.getInitialIndex() - finalCurrentHeadPosition)));
                }
            }

            if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.get(0);
            }

            if (currentProcess != null && currentProcess.getInitialIndex() == currentHeadPosition) {
                queue.remove(0);
                sumOfWaitingTime += currentProcess.getWaitingTime();
                currentProcess = (queue.isEmpty() ? null : queue.get(0));
            }

            if (currentProcess != null) {
                currentHeadPosition += Integer.compare(currentProcess.getInitialIndex(), currentHeadPosition);
                way++;
            }

            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("SSTF", way, 0, 0, 0, sumOfWaitingTime / amountOfProcesses);
    }
}
