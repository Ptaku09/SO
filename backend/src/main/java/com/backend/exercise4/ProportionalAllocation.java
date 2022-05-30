package com.backend.exercise4;

import java.util.Queue;

public class ProportionalAllocation extends Allocation {
    public ProportionalAllocation(int numberOfProcesses, int numberOfFrames, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        super("Proportional Allocation", numberOfProcesses, numberOfFrames, globalTestSequence, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect);
    }

    protected void calculateFramesPerProcess() {
        for (int i = 0; i < framesPerProcess.length; i++) {
            framesPerProcess[i] = new int[(int) Math.ceil((double) numberOfDifferentPagesPerProcess[i] / numberOfProcesses)];
            recentUsePerProcess[i] = new int[(int) Math.ceil((double) numberOfDifferentPagesPerProcess[i] / numberOfProcesses)];
        }
    }
}
