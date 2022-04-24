package com.backend.exercise2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FDSCAN extends Algorithm {
    public FDSCAN(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        List<Process> queue = new ArrayList<>();
        List<Process> priorityQueue = new ArrayList<>();
        double amountOfProcesses = processes.size();
        int finishedRealTime = 0;
        int unfinishedRealTime = 0;
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int displacements = 0;
        int currentHeadPosition = 0;

        while (!processes.isEmpty() || !queue.isEmpty() || !priorityQueue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                Process process = processes.remove(0);

                if (process.isRealTime()) {
                    if (process.getDeadLine() - Math.abs(currentHeadPosition - process.getInitialIndex()) >= 0) {
                        priorityQueue.add(process);
                        priorityQueue.sort(Comparator.comparing(Process::getDeadLine));
                    } else {
                        sumOfWaitingTime += process.getInitialDeadLine();
                        unfinishedRealTime++;
                    }
                } else {
                    queue.add(process);
                }
            }

            int finalCurrentHeadPosition = currentHeadPosition;
            queue.sort(Comparator.comparingInt(p -> p.getInitialIndex() >= finalCurrentHeadPosition ? p.getInitialIndex() - finalCurrentHeadPosition : driveSize + 1));

            while (!queue.isEmpty() && currentHeadPosition == queue.get(0).getInitialIndex()) {
                sumOfWaitingTime += queue.get(0).getWaitingTime();
                queue.remove(0);
            }

            while (!priorityQueue.isEmpty() && priorityQueue.get(0).getInitialIndex() == currentHeadPosition) {
                if (priorityQueue.get(0).getDeadLine() >= 0) {
                    sumOfWaitingTime += priorityQueue.get(0).getWaitingTime();
                    finishedRealTime++;
                } else {
                    sumOfWaitingTime += priorityQueue.get(0).getInitialDeadLine();
                    unfinishedRealTime++;
                }

                priorityQueue.remove(0);
            }

            if (!priorityQueue.isEmpty())
                currentHeadPosition += Integer.compare(priorityQueue.get(0).getInitialIndex(), currentHeadPosition);
            else {
                currentHeadPosition = (currentHeadPosition + 1) % driveSize;

                if (currentHeadPosition == 0)
                    displacements++;
            }

            way++;
            priorityQueue.forEach(Process::updateRealTime);
            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("FD-SCAN", way, displacements, unfinishedRealTime, finishedRealTime, sumOfWaitingTime / amountOfProcesses);
    }
}
