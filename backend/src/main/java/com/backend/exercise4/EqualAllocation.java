package com.backend.exercise4;

import java.util.Queue;

public class EqualAllocation extends Allocation {
    public EqualAllocation(int numberOfProcesses, int numberOfFrames, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        super("Equal Allocation", numberOfProcesses, numberOfFrames, globalTestSequence, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect);
    }

    @Override
    protected void calculateFramesPerProcess() {
        this.framesPerProcess = new int[numberOfProcesses][numberOfFrames / numberOfProcesses];
        this.recentUsePerProcess = new int[numberOfProcesses][numberOfFrames / numberOfProcesses];
    }
}
