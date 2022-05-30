package com.backend.exercise4;

import java.util.Arrays;
import java.util.Queue;

public class ProportionalAllocation {
    private final Queue<Recall> globalTestSequence;
    private final int[][] framesPerProcess;
    private final int numberOfProcesses;
    private final int[][] recentUsePerProcess;
    private final int[] numberOfDifferentPagesPerProcess;
    private final int scuffleTime;
    private final int scufflePercentToDetect;
    private int maxAcceptableScuffle;

    public ProportionalAllocation(int numberOfProcesses, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        this.numberOfProcesses = numberOfProcesses;
        this.globalTestSequence = globalTestSequence;
        this.numberOfDifferentPagesPerProcess = numberOfDifferentPagesPerProcess;
        this.framesPerProcess = new int[numberOfProcesses][];
        this.recentUsePerProcess = new int[numberOfProcesses][];
        this.scuffleTime = scuffleTime;
        this.scufflePercentToDetect = scufflePercentToDetect;

        init();
    }

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

        return new Results("Proportional allocation", errors, errorsPerProcess, scuffleErrors);
    }

    private void init() {
        calculateFramesPerProcess();
        calculateMaxAcceptableScuffle();
        fillFramesPerProcessArray();
        fillRecentUseArray();
    }

    private void calculateFramesPerProcess() {
        for (int i = 0; i < framesPerProcess.length; i++) {
            framesPerProcess[i] = new int[(int) Math.ceil((double) numberOfDifferentPagesPerProcess[i] / numberOfProcesses)];
        }
    }

    private void calculateMaxAcceptableScuffle() {
        this.maxAcceptableScuffle = (int) Math.ceil((double) scuffleTime / 100 * scufflePercentToDetect);
    }

    private void fillFramesPerProcessArray() {
        for (int[] perProcess : framesPerProcess) Arrays.fill(perProcess, -1);
    }

    private void fillRecentUseArray() {
        for (int i = 0; i < recentUsePerProcess.length; i++) {
            recentUsePerProcess[i] = new int[framesPerProcess[i].length];
            Arrays.fill(recentUsePerProcess[i], -1);
        }
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

    private void updateRecentUse(int currentProcessNumber) {
        for (int i = 0; i < recentUsePerProcess[currentProcessNumber].length; i++)
            if (recentUsePerProcess[currentProcessNumber][i] != -1)
                recentUsePerProcess[currentProcessNumber][i]++;
    }
}
