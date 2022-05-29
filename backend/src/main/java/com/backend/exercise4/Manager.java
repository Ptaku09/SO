package com.backend.exercise4;

import java.util.*;

public class Manager {
    private final static int BACKUP_NUMBER_OF_PROCESSES = 10;
    private final static int BACKUP_NUMBER_OF_PAGES_PER_PROCESS = 1000;
    private final static int BACKUP_NUMBER_OF_FRAMES = 50;
    private final static int BACKUP_SCUFFLE_TIME = 50;
    private final static int BACKUP_SCUFFLE_PERCENT_TO_DETECT = 50;
    private final int numberOfProcesses;
    private final int testSequenceLengthPerProcess;
    private final int numberOfFrames;
    private final Queue<Recall> globalTestSequence = new LinkedList<>();
    private final List<Queue<Recall>> processesTestSequence = new ArrayList<>();
    private final List<Integer> pages = new ArrayList<>();
    private final int scuffleTime;
    private final int scufflePercentToDetect;

    public static void main(String[] args) {
        new Manager(BACKUP_NUMBER_OF_PROCESSES, BACKUP_NUMBER_OF_PAGES_PER_PROCESS, BACKUP_NUMBER_OF_FRAMES, BACKUP_SCUFFLE_TIME, BACKUP_SCUFFLE_PERCENT_TO_DETECT).runSimulation();
    }

    public Manager(int numberOfProcesses, int testSequenceLengthPerProcess, int numberOfFrames, int scuffleTime, int scufflePercentToDetect) {
        int[] validatedData = validateData(numberOfProcesses, testSequenceLengthPerProcess, numberOfFrames, scuffleTime, scufflePercentToDetect);
        this.numberOfProcesses = validatedData[0];
        this.testSequenceLengthPerProcess = validatedData[1];
        this.numberOfFrames = validatedData[2];
        this.scuffleTime = validatedData[3];
        this.scufflePercentToDetect = validatedData[4];

        init();
    }

    public List<Results> runSimulation() {
        List<Results> results = new ArrayList<>();

        results.add(new EqualAllocation(numberOfProcesses, numberOfFrames, globalTestSequence, scuffleTime, scufflePercentToDetect).run());

        return results;
    }

    private int[] validateData(int numberOfProcesses, int testSequenceLengthPerProcess, int numberOfFrames, int scuffleTime, int scufflePercentToDetect) {
        if (numberOfProcesses <= 0 || testSequenceLengthPerProcess <= 0 || numberOfFrames <= 0 || scuffleTime <= 0 || scufflePercentToDetect <= 0 || scufflePercentToDetect > 100)
            return new int[]{BACKUP_NUMBER_OF_PROCESSES, BACKUP_NUMBER_OF_PAGES_PER_PROCESS, BACKUP_NUMBER_OF_FRAMES, BACKUP_SCUFFLE_TIME, BACKUP_SCUFFLE_PERCENT_TO_DETECT};
        else if (testSequenceLengthPerProcess * numberOfProcesses < numberOfFrames)
            return new int[]{numberOfProcesses, numberOfFrames, numberOfFrames, scuffleTime, scufflePercentToDetect};

        return new int[]{numberOfProcesses, testSequenceLengthPerProcess, numberOfFrames, scuffleTime, scufflePercentToDetect};
    }

    private void init() {
        fillPages();
        generateTestSequences();
        createGlobalTestSequence();
    }

    private void fillPages() {
        for (int i = 0; i < this.numberOfProcesses * this.testSequenceLengthPerProcess; i++)
            this.pages.add(i);
    }

    private void generateTestSequences() {
        Random random = new Random();

        for (int i = 0; i < this.numberOfProcesses; i++) {
            processesTestSequence.add(new LinkedList<>());
            Queue<Recall> processTestSequence = processesTestSequence.get(i);
            int k = 0;

            while (k < this.testSequenceLengthPerProcess) {
                int amount = generateProcessSubsequenceLength();
                int[] subsequence = generateSubsequence();

                for (int j = 0; j < amount && k < testSequenceLengthPerProcess; j++) {
                    int randomIndex = random.nextInt(0, subsequence.length);
                    processTestSequence.add(new Recall(i, validateGeneratedNumber(subsequence, randomIndex, !processTestSequence.isEmpty() ? processTestSequence.peek().getPageNumber() : -1)));
                    k++;
                }
            }
        }
    }

    private void createGlobalTestSequence() {
        Random random = new Random();

        while (!processesTestSequence.isEmpty()) {
            int randomProcessIndex = random.nextInt(0, processesTestSequence.size());
            Queue<Recall> randomProcess = processesTestSequence.get(randomProcessIndex);

            globalTestSequence.add(randomProcess.poll());

            if (randomProcess.isEmpty())
                processesTestSequence.remove(randomProcessIndex);
        }
    }

    private int generateProcessSubsequenceLength() {
        Random random = new Random();

        if (this.testSequenceLengthPerProcess < 100)
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.1), (int) (this.testSequenceLengthPerProcess * 0.3));
        else if (this.testSequenceLengthPerProcess < 1000)
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.02), (int) (this.testSequenceLengthPerProcess * 0.08));
        else
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.005), (int) (this.testSequenceLengthPerProcess * 0.02));
    }

    private int[] generateSubsequence() {
        int[] subsequence = new int[this.numberOfFrames / this.numberOfProcesses];
        Random random = new Random();

        for (int i = 0; i < subsequence.length; i++)
            subsequence[i] = this.pages.remove(random.nextInt(0, this.pages.size()));

        return subsequence;
    }

    private int validateGeneratedNumber(int[] subsequence, int subsequenceIndex, int lastNumber) {
        return subsequence[subsequenceIndex] == lastNumber ? (subsequence[(subsequenceIndex + 1) % (this.numberOfFrames / this.numberOfProcesses)]) : subsequence[subsequenceIndex];
    }
}
