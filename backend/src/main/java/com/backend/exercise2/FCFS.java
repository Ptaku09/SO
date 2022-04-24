package com.backend.exercise2;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FCFS extends Algorithm {
    public FCFS(int driveSize, int initialHeadPosition, List<Process> processes) {
        super(driveSize, initialHeadPosition, processes);
    }

    @Override
    public Results run() {
        Queue<Process> queue = new LinkedList<>();
        double amountOfProcesses = processes.size();
        double sumOfWaitingTime = 0;
        double currentTime = 0;
        double way = 0;
        int currentHeadPosition = initialHeadPosition;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime)
                queue.add(processes.remove(0));

            while (!queue.isEmpty() && queue.peek().getInitialIndex() == currentHeadPosition) {
                sumOfWaitingTime += queue.element().getWaitingTime();
                queue.remove();
            }

            if (!queue.isEmpty()) {
                currentHeadPosition += Integer.compare(queue.peek().getInitialIndex(), currentHeadPosition);
                way++;
            }

            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("FCFS", way, -1, -1, -1, sumOfWaitingTime / amountOfProcesses);
    }
}
