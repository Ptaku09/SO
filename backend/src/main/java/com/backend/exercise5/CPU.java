package com.backend.exercise5;

import java.util.ArrayList;
import java.util.List;

public class CPU {
    private final List<Process> activeProcesses;
    private int load;

    public CPU() {
        this.activeProcesses = new ArrayList<>();
        this.load = 0;
    }

    public void reset() {
        this.load = 0;
    }

    public void updateProcesses() {
        List<Integer> finishedProcesses = new ArrayList<>();

        for (Process process : activeProcesses) {
            process.update();

            if (process.getTimeToFinish() == 0) {
                finishedProcesses.add(activeProcesses.indexOf(process));
                load -= process.getPowerDemand();
            }
        }

        for (int i : finishedProcesses)
            activeProcesses.remove(i);
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
