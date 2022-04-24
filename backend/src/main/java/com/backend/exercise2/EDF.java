package com.backend.exercise2;

import java.util.*;

public class EDF extends Algorithm {
    public EDF(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        Queue<Process> queue = new LinkedList<>();
        List<Process> priorityQueue = new ArrayList<>();
        double amountOfProcesses = processes.size();
        int finishedRealTime = 0;
        int unfinishedRealTime = 0;
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int currentHeadPosition = initialHeadPosition;
        Process currentProcess = null;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                Process process = processes.remove(0);

                if (process.isRealTime()) {
                    priorityQueue.add(process);
                    priorityQueue.sort(Comparator.comparing(Process::getDeadLine));
                } else
                    queue.add(process);
            }

            if (!priorityQueue.isEmpty()) {
                while (!priorityQueue.isEmpty() && priorityQueue.get(0).getDeadLine() <= 0) {
                    unfinishedRealTime++;
                    sumOfWaitingTime += priorityQueue.get(0).getInitialDeadLine();
                    priorityQueue.remove(0);
                }

                currentProcess = !priorityQueue.isEmpty() ? priorityQueue.get(0) : null;
            } else if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.peek();
            }

            while (currentProcess != null && currentProcess.getInitialIndex() == currentHeadPosition) {
                if (currentProcess.isRealTime()) {
                    if (currentProcess.getDeadLine() < 0) {
                        unfinishedRealTime++;
                        sumOfWaitingTime += currentProcess.getInitialDeadLine();
                    } else {
                        finishedRealTime++;
                        sumOfWaitingTime += currentProcess.getWaitingTime();
                    }

                    priorityQueue.remove(currentProcess);
                    currentProcess = (!priorityQueue.isEmpty() ? priorityQueue.get(0) : queue.isEmpty() ? null : queue.peek());
                } else {
                    queue.remove();
                    sumOfWaitingTime += currentProcess.getWaitingTime();
                    currentProcess = (queue.isEmpty() ? null : queue.peek());
                }
            }

            if (currentProcess != null) {
                currentHeadPosition += Integer.compare(currentProcess.getInitialIndex(), currentHeadPosition);
                way++;
            }

            priorityQueue.forEach(Process::updateRealTime);
            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("EDF", way, -1, unfinishedRealTime, finishedRealTime, sumOfWaitingTime / amountOfProcesses);
    }
}
