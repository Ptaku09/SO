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
        Process currentProcess = null;

        while (!processes.isEmpty() || !queue.isEmpty()) {
            while (!processes.isEmpty() && processes.get(0).getInitialTime() == currentTime) {
                queue.add(processes.remove(0));
            }

            if (currentProcess == null && !queue.isEmpty()) {
                currentProcess = queue.peek();
            }

            while (currentProcess != null && currentProcess.getInitialIndex() == currentHeadPosition) {
                queue.remove();
                sumOfWaitingTime += currentProcess.getWaitingTime();
                currentProcess = (queue.isEmpty() ? null : queue.peek());
            }

            if (currentProcess != null) {
                currentHeadPosition += Integer.compare(currentProcess.getInitialIndex(), currentHeadPosition);
                way++;
            }

            queue.forEach(Process::update);
            currentTime++;
        }

        return new Results("FCFS", way, 0, -1, -1, sumOfWaitingTime / amountOfProcesses);
    }
}
