package com.backend.exercise4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class PageFaultsControl implements Runnable {
    private final Queue<Recall> globalTestSequence;
    private final int numberOfProcesses;
    private final int numberOfFrames;
    private final int scuffleTime;
    private final int scufflePercentToDetect;
    private final int[] numberOfDifferentPagesPerProcess;
    private final String algorithmName;
    private final int[][] framesPerProcess;
    private final int[][] recentUsePerProcess;
    private final float[] ppf;
    private final static int TIME_WINDOW = 50;
    private final int[] processErrorsInTime;
    private final static float MIN_ERRORS = 0.2f;
    private final static float MAX_ERRORS = 0.8f;
    private final List<Integer> stoppedProcesses = new ArrayList<>();
    private int maxAcceptableScuffle;
    private int freeFrames;

    public PageFaultsControl(String algorithmName, int numberOfProcesses, int numberOfFrames, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        this.globalTestSequence = globalTestSequence;
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfFrames = numberOfFrames;
        this.framesPerProcess = new int[numberOfProcesses][];
        this.recentUsePerProcess = new int[numberOfProcesses][];
        this.scuffleTime = scuffleTime;
        this.scufflePercentToDetect = scufflePercentToDetect;
        this.numberOfDifferentPagesPerProcess = numberOfDifferentPagesPerProcess;
        this.algorithmName = algorithmName;

        this.ppf = new float[numberOfProcesses];
        this.processErrorsInTime = new int[numberOfProcesses];

        init();
    }

    @Override
    public Results run() {
        int errors = 0;
        int[] errorsPerProcess = new int[numberOfProcesses];
        int scuffleErrors = 0;
        int scuffleTimeCounter = 0;
        int scuffleErrorsCounter = 0;

        int processErrorsTimer = 0;

        while (!globalTestSequence.isEmpty()) {
            boolean isFound = false;
            Recall currentPage = globalTestSequence.poll();
            int currentProcessNumber = currentPage.getProcessNumber();
            int currentPageNumber = currentPage.getPageNumber();

            if (stoppedProcesses.contains(currentProcessNumber)) continue;

            for (int j = 0; j < framesPerProcess[currentProcessNumber].length; j++)
                if (framesPerProcess[currentProcessNumber][j] == currentPageNumber) {
                    isFound = true;
                    recentUsePerProcess[currentProcessNumber][j] = 0;
                    break;
                }

            scuffleTimeCounter++;

            if (scuffleErrorsCounter != 0 && scuffleTimeCounter > scuffleTime)
                scuffleErrorsCounter--;


            processErrorsTimer++;

            if (processErrorsTimer > TIME_WINDOW) {
                for (int i = 0; i < numberOfProcesses; i++)
                    if (processErrorsInTime[i] > 0)
                        processErrorsInTime[i]--;

                calculatePPF();
            }

            if (!isFound) {
                errors++;
                errorsPerProcess[currentProcessNumber]++;

                processErrorsInTime[currentProcessNumber]++;
                if (processErrorsTimer > TIME_WINDOW) checkStatus(currentProcessNumber);

                scuffleErrorsCounter++;

                if (scuffleTimeCounter > scuffleTime)
                    if (scuffleErrorsCounter > maxAcceptableScuffle)
                        scuffleErrors++;

                updatePhysicalMemory(currentProcessNumber, currentPageNumber);
            }

            updateRecentUse(currentProcessNumber);
        }

        return new Results(algorithmName, errors, errorsPerProcess, scuffleErrors, stoppedProcesses.size());
    }

    private void checkStatus(int currentProcessNumber) {
        calculatePPF(currentProcessNumber);

        if (ppf[currentProcessNumber] > MAX_ERRORS) {
            if (freeFrames > 0) {
                framesPerProcess[currentProcessNumber] = Arrays.copyOf(framesPerProcess[currentProcessNumber], framesPerProcess[currentProcessNumber].length + 1);
                framesPerProcess[currentProcessNumber][framesPerProcess[currentProcessNumber].length - 1] = -1;
                recentUsePerProcess[currentProcessNumber] = Arrays.copyOf(recentUsePerProcess[currentProcessNumber], recentUsePerProcess[currentProcessNumber].length + 1);
                recentUsePerProcess[currentProcessNumber][recentUsePerProcess[currentProcessNumber].length - 1] = -1;
                freeFrames--;
            } else {
                stoppedProcesses.add(currentProcessNumber);
            }
        } else if (ppf[currentProcessNumber] < MIN_ERRORS && framesPerProcess[currentProcessNumber].length > 1) {
            framesPerProcess[currentProcessNumber] = Arrays.copyOfRange(framesPerProcess[currentProcessNumber], 0, framesPerProcess[currentProcessNumber].length - 1);
            recentUsePerProcess[currentProcessNumber] = Arrays.copyOfRange(recentUsePerProcess[currentProcessNumber], 0, recentUsePerProcess[currentProcessNumber].length - 1);
            freeFrames++;
        }
    }

    private void calculatePPF() {
        for (int i = 0; i < ppf.length; i++) {
            ppf[i] = (float) processErrorsInTime[i] / TIME_WINDOW;

            if (ppf[i] < MIN_ERRORS && framesPerProcess[i].length > 1) {
                framesPerProcess[i] = Arrays.copyOfRange(framesPerProcess[i], 0, framesPerProcess[i].length - 1);
                recentUsePerProcess[i] = Arrays.copyOfRange(recentUsePerProcess[i], 0, recentUsePerProcess[i].length - 1);
                freeFrames++;
            }
        }
    }

    private void calculatePPF(int processNumber) {
        ppf[processNumber] = (float) processErrorsInTime[processNumber] / TIME_WINDOW;
    }

    private void init() {
        calculateFramesPerProcess();
        calculateMaxAcceptableScuffle();
        fillFramesPerProcessArray();
        fillRecentUseArray();
    }

    private void calculateFramesPerProcess() {
        freeFrames = numberOfFrames;
        int sum = calculateSumOfUsedPages();

        for (int i = 0; i < framesPerProcess.length; i++) {
            framesPerProcess[i] = new int[(int) Math.ceil((double) numberOfDifferentPagesPerProcess[i] / sum * numberOfFrames)];
            recentUsePerProcess[i] = new int[(int) Math.ceil((double) numberOfDifferentPagesPerProcess[i] / sum * numberOfFrames)];

            freeFrames -= framesPerProcess[i].length;
        }
    }

    private int calculateSumOfUsedPages() {
        int sum = 0;

        for (int i = 0; i < numberOfProcesses; i++)
            sum += numberOfDifferentPagesPerProcess[i];

        return sum;
    }

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

    private void updateRecentUse(int currentProcessNumber) {
        for (int i = 0; i < recentUsePerProcess[currentProcessNumber].length; i++)
            if (recentUsePerProcess[currentProcessNumber][i] != -1)
                recentUsePerProcess[currentProcessNumber][i]++;
    }
}
