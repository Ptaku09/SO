package com.backend.exercise4;

import java.util.Arrays;
import java.util.Queue;

public abstract class Allocation implements Runnable {
    protected final Queue<Recall> globalTestSequence;
    protected final int numberOfProcesses;
    protected final int numberOfFrames;
    protected final int scuffleTime;
    protected final int scufflePercentToDetect;
    protected final int[] numberOfDifferentPagesPerProcess;
    protected final String algorithmName;
    protected int[][] framesPerProcess;
    protected int[][] recentUsePerProcess;
    protected int maxAcceptableScuffle;

    public Allocation(String algorithmName, int numberOfProcesses, int numberOfFrames, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        this.globalTestSequence = globalTestSequence;
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfFrames = numberOfFrames;
        this.framesPerProcess = new int[numberOfProcesses][];
        this.recentUsePerProcess = new int[numberOfProcesses][];
        this.scuffleTime = scuffleTime;
        this.scufflePercentToDetect = scufflePercentToDetect;
        this.numberOfDifferentPagesPerProcess = numberOfDifferentPagesPerProcess;
        this.algorithmName = algorithmName;

        init();
    }

    @Override
    public Results run() {
        int errors = 0;
        int[] errorsPerProcess = new int[numberOfProcesses];
        int scuffleErrors = 0;
        int scuffleTimeCounter = 0;
        int scuffleErrorsCounter = 0;

        while (!globalTestSequence.isEmpty()) {
            boolean isFound = false;
            Recall currentPage = globalTestSequence.poll();
            int currentProcessNumber = currentPage.getProcessNumber();
            int currentPageNumber = currentPage.getPageNumber();

            for (int j = 0; j < framesPerProcess[currentProcessNumber].length; j++)
                if (framesPerProcess[currentProcessNumber][j] == currentPageNumber) {
                    isFound = true;
                    recentUsePerProcess[currentProcessNumber][j] = 0;
                    break;
                }

            scuffleTimeCounter++;

            if (!isFound) {
                errors++;
                errorsPerProcess[currentProcessNumber]++;

                scuffleErrorsCounter++;
                if (scuffleTimeCounter % this.scuffleTime == 0) {
                    if (scuffleErrorsCounter > this.maxAcceptableScuffle)
                        scuffleErrors++;

                    scuffleTimeCounter = 0;
                }

                updatePhysicalMemory(currentProcessNumber, currentPageNumber);
            } else {
                scuffleErrorsCounter = scuffleErrorsCounter - 1 < 0 ? 0 : scuffleErrorsCounter;
            }

            updateRecentUse(currentProcessNumber);
        }

        return new Results(algorithmName, errors, errorsPerProcess, scuffleErrors);
    }

    private void init() {
        calculateFramesPerProcess();
        calculateMaxAcceptableScuffle();
        fillFramesPerProcessArray();
        fillRecentUseArray();
    }

    protected abstract void calculateFramesPerProcess();

    private void calculateMaxAcceptableScuffle() {
        this.maxAcceptableScuffle = (int) Math.ceil((double) scuffleTime / 100 * scufflePercentToDetect);
    }

    private void fillFramesPerProcessArray() {
        for (int[] perProcess : framesPerProcess) Arrays.fill(perProcess, -1);
    }

    private void fillRecentUseArray() {
        for (int[] recentUse : recentUsePerProcess) Arrays.fill(recentUse, -1);
    }

    private void updatePhysicalMemory(int currentProcessNumber, int currentPageNumber) {
        int maxRecentUse = 0;
        int maxRecentUseIndex = 0;

        for (int i = 0; i < framesPerProcess[currentProcessNumber].length; i++) {
            if (framesPerProcess[currentProcessNumber][i] == -1 || recentUsePerProcess[currentProcessNumber][i] == -1) {
                framesPerProcess[currentProcessNumber][i] = currentPageNumber;
                recentUsePerProcess[currentProcessNumber][i] = 0;
                return;
            }

            if (recentUsePerProcess[currentProcessNumber][i] > maxRecentUse) {
                maxRecentUse = recentUsePerProcess[currentProcessNumber][i];
                maxRecentUseIndex = i;
            }
        }

        framesPerProcess[currentProcessNumber][maxRecentUseIndex] = currentPageNumber;
        recentUsePerProcess[currentProcessNumber][maxRecentUseIndex] = 0;
    }

    protected void updateRecentUse(int currentProcessNumber) {
        for (int i = 0; i < recentUsePerProcess[currentProcessNumber].length; i++)
            if (recentUsePerProcess[currentProcessNumber][i] != -1)
                recentUsePerProcess[currentProcessNumber][i]++;
    }
}
