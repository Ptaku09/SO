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

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                queue.add(processes.remove(0));

                if (queue.size() > 1) {
                    int finalCurrentHeadPosition = currentHeadPosition;
                    queue.subList(1, queue.size()).sort(Comparator.comparingInt(p -> Math.abs(p.getInitialIndex() - finalCurrentHeadPosition)));
                }
            }

            while (!queue.isEmpty() && queue.get(0).getInitialIndex() == currentHeadPosition) {
                sumOfWaitingTime += queue.get(0).getWaitingTime();
                queue.remove(0);
            }

            if (!queue.isEmpty()) {
                currentHeadPosition += Integer.compare(queue.get(0).getInitialIndex(), currentHeadPosition);
                way++;
            }

            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("SSTF", way, -1, -1, -1, sumOfWaitingTime / amountOfProcesses);
    }
}
