package com.backend.exercise4;

import java.util.*;

public class ZoneModel implements Runnable {
    private final static int TIME_WINDOW = 100;
    private final Queue<Recall> globalTestSequence;
    private final int numberOfProcesses;
    private final int numberOfFrames;
    private final int scuffleTime;
    private final int scufflePercentToDetect;
    private final int[] numberOfDifferentPagesPerProcess;
    private final int[][] framesPerProcess;
    private final int[][] recentUsePerProcess;
    private final Set<Integer> stoppedProcesses = new HashSet<>();
    private final HashMap<Integer, HashSet<Integer>> pagesPerProcess = new HashMap<>();
    private int maxAcceptableScuffle;
    private int freeFrames;


    public ZoneModel(int numberOfProcesses, int numberOfFrames, Queue<Recall> globalTestSequence, int[] numberOfDifferentPagesPerProcess, int scuffleTime, int scufflePercentToDetect) {
        this.globalTestSequence = globalTestSequence;
        this.numberOfProcesses = numberOfProcesses;
        this.numberOfFrames = numberOfFrames;
        this.framesPerProcess = new int[numberOfProcesses][];
        this.recentUsePerProcess = new int[numberOfProcesses][];
        this.scuffleTime = scuffleTime;
        this.scufflePercentToDetect = scufflePercentToDetect;
        this.numberOfDifferentPagesPerProcess = numberOfDifferentPagesPerProcess;

        init();
    }

    @Override
    public Results run() {
        int errors = 0;
        int[] errorsPerProcess = new int[numberOfProcesses];
        int scuffleErrors = 0;
        int scuffleTimeCounter = 0;
        int scuffleErrorsCounter = 0;
        int timer = 0;

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

            timer++;

            if (!pagesPerProcess.containsKey(currentProcessNumber))
                pagesPerProcess.put(currentProcessNumber, new HashSet<>());

            pagesPerProcess.get(currentProcessNumber).add(currentPageNumber);

            if (timer % (TIME_WINDOW / 2) == 0) {
                int neededFrames = calculateNeededFrames(currentProcessNumber);
                makeSpace(neededFrames, currentProcessNumber);
                addFrames(neededFrames, currentProcessNumber);
                resetCounters();
            }

            if (!isFound) {
                errors++;
                errorsPerProcess[currentProcessNumber]++;

                scuffleErrorsCounter++;

                if (scuffleTimeCounter > scuffleTime)
                    if (scuffleErrorsCounter > maxAcceptableScuffle)
                        scuffleErrors++;

                updatePhysicalMemory(currentProcessNumber, currentPageNumber);
            }

            updateRecentUse(currentProcessNumber);
        }

        return new Results("Zone Model", errors, errorsPerProcess, scuffleErrors, stoppedProcesses.size());
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

    private int calculateNeededFrames(int currentProcessNumber) {
        return pagesPerProcess.get(currentProcessNumber).size();
    }

    private void makeSpace(int neededFrames, int currentProcessNumber) {
        if (neededFrames > freeFrames)
            for (Integer key : pagesPerProcess.keySet()) {
                if (key == currentProcessNumber) continue;
                stoppedProcesses.add(key);
                freeFrames += framesPerProcess[key].length;

                if (neededFrames <= freeFrames) break;
            }
    }

    private void addFrames(int neededFrames, int currentProcessNumber) {
        int framesToAdd = neededFrames - framesPerProcess[currentProcessNumber].length;

        if (framesToAdd > 0) {
            framesPerProcess[currentProcessNumber] = Arrays.copyOf(framesPerProcess[currentProcessNumber], framesPerProcess[currentProcessNumber].length + framesToAdd);
            recentUsePerProcess[currentProcessNumber] = Arrays.copyOf(recentUsePerProcess[currentProcessNumber], recentUsePerProcess[currentProcessNumber].length + framesToAdd);
        } else if (framesToAdd < 0) {
            if (framesPerProcess[currentProcessNumber].length + framesToAdd < 1) {
                framesPerProcess[currentProcessNumber] = new int[1];
                framesPerProcess[currentProcessNumber][0] = -1;
                recentUsePerProcess[currentProcessNumber] = new int[1];
                recentUsePerProcess[currentProcessNumber][0] = -1;
            } else {
                framesPerProcess[currentProcessNumber] = Arrays.copyOfRange(framesPerProcess[currentProcessNumber], 0, framesPerProcess[currentProcessNumber].length + framesToAdd);
                recentUsePerProcess[currentProcessNumber] = Arrays.copyOfRange(recentUsePerProcess[currentProcessNumber], 0, recentUsePerProcess[currentProcessNumber].length + framesToAdd);
            }
        }

        freeFrames -= framesToAdd;
    }

    private void resetCounters() {
        for (Integer key : pagesPerProcess.keySet())
            pagesPerProcess.get(key).clear();
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
