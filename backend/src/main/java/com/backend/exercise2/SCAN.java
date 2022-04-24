package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SCAN extends Algorithm {
    public SCAN(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        List<Process> queue = new ArrayList<>();
        double amountOfProcesses = processes.size();
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int currentHeadPosition = 0;
        boolean isGoingToRight = true;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime)
                queue.add(processes.remove(0));

            if (isGoingToRight) {
                int finalCurrentHeadPosition = currentHeadPosition;
                queue.sort(Comparator.comparingInt(p -> p.getInitialIndex() >= finalCurrentHeadPosition ? p.getInitialIndex() - finalCurrentHeadPosition : driveSize + 1));
            } else {
                int finalCurrentHeadPosition = currentHeadPosition;
                queue.sort(Comparator.comparingInt(p -> p.getInitialIndex() <= finalCurrentHeadPosition ? Math.abs(p.getInitialIndex() - finalCurrentHeadPosition) : driveSize + 1));
            }

            while (!queue.isEmpty() && queue.get(0).getInitialIndex() == currentHeadPosition) {
                sumOfWaitingTime += queue.get(0).getWaitingTime();
                queue.remove(0);
            }

            if (isGoingToRight) {
                currentHeadPosition++;

                if (currentHeadPosition == driveSize)
                    isGoingToRight = false;
            } else {
                currentHeadPosition--;

                if (currentHeadPosition == 0)
                    isGoingToRight = true;
            }

            way++;
            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("SCAN", way, -1, -1, -1, sumOfWaitingTime / amountOfProcesses);
    }
}
