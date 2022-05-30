package com.backend.exercise4;

import java.util.*;
import java.util.stream.Collectors;

public class Manager {
    private final static int BACKUP_NUMBER_OF_PROCESSES = 10;
    private final static int BACKUP_TEST_SEQUENCE_LENGTH_PER_PROCESS = 1000;
    private final static int BACKUP_NUMBER_OF_FRAMES = 50;
    private final static int BACKUP_SCUFFLE_TIME = 50;
    private final static int BACKUP_SCUFFLE_PERCENT_TO_DETECT = 50;
    private final int numberOfProcesses;
    private final int testSequenceLengthPerProcess;
    private final int numberOfFrames;
    private final Queue<Recall> globalTestSequence1 = new LinkedList<>();
    private final Queue<Recall> globalTestSequence2 = new LinkedList<>();
    private final Queue<Recall> globalTestSequence3 = new LinkedList<>();
    private final Queue<Recall> globalTestSequence4 = new LinkedList<>();
    private final List<Queue<Recall>> processesTestSequence = new ArrayList<>();
    private final List<Integer> pages = new ArrayList<>();
    private final int scuffleTime;
    private final int scufflePercentToDetect;
    private final int[] numberOfDifferentPagesPerProcess;

    public Manager(int numberOfProcesses, int testSequenceLengthPerProcess, int numberOfFrames, int scuffleTime, int scufflePercentToDetect) {
        int[] validatedData = validateData(numberOfProcesses, testSequenceLengthPerProcess, numberOfFrames, scuffleTime, scufflePercentToDetect);
        this.numberOfProcesses = validatedData[0];
        this.testSequenceLengthPerProcess = validatedData[1];
        this.numberOfFrames = validatedData[2];
        this.scuffleTime = validatedData[3];
        this.scufflePercentToDetect = validatedData[4];
        this.numberOfDifferentPagesPerProcess = new int[validatedData[0]];

        init();
    }

    public List<Results> runSimulation() {
        List<Results> results = new ArrayList<>();

        results.add(new EqualAllocation(numberOfProcesses, numberOfFrames, globalTestSequence1, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect).run());
        results.add(new ProportionalAllocation(numberOfProcesses, numberOfFrames, globalTestSequence2, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect).run());
        results.add(new PageFaultsControl("Page Faults Control", numberOfProcesses, numberOfFrames, globalTestSequence3, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect).run());
        results.add(new ZoneModel("Zone Model", numberOfProcesses, numberOfFrames, globalTestSequence4, numberOfDifferentPagesPerProcess, scuffleTime, scufflePercentToDetect).run());

        return results;
    }

    private int[] validateData(int numberOfProcesses, int testSequenceLengthPerProcess, int numberOfFrames, int scuffleTime, int scufflePercentToDetect) {
        if (numberOfProcesses <= 0 || testSequenceLengthPerProcess <= 0 || numberOfFrames <= 0 || scuffleTime <= 0 || scufflePercentToDetect <= 0 || scufflePercentToDetect > 100)
            return new int[]{BACKUP_NUMBER_OF_PROCESSES, BACKUP_TEST_SEQUENCE_LENGTH_PER_PROCESS, BACKUP_NUMBER_OF_FRAMES, BACKUP_SCUFFLE_TIME, BACKUP_SCUFFLE_PERCENT_TO_DETECT};
        else if (testSequenceLengthPerProcess * numberOfProcesses < numberOfFrames)
            return new int[]{numberOfProcesses, numberOfFrames, numberOfFrames, scuffleTime, scufflePercentToDetect};

        return new int[]{numberOfProcesses, testSequenceLengthPerProcess, numberOfFrames, scuffleTime, scufflePercentToDetect};
    }

    private void init() {
        fillPages();
        generateTestSequences();
        createGlobalTestSequences();
    }

    private void fillPages() {
        for (int i = 0; i < this.numberOfProcesses * this.testSequenceLengthPerProcess * 2; i++)
            this.pages.add(i);
    }

    private void generateTestSequences() {
        Random random = new Random();

        for (int i = 0; i < this.numberOfProcesses; i++) {
            processesTestSequence.add(new LinkedList<>());
            Queue<Recall> processTestSequence = processesTestSequence.get(i);
            int k = 0;

            while (k < this.testSequenceLengthPerProcess) {
                int subsequenceLength = generateProcessSubsequenceLength();
                int amountOfSubsequenceElements = generateAmountOfSubsequenceElements();
                int[] subsequenceElements = generateSubsequenceElements(amountOfSubsequenceElements);

                // Create subsequence set for counting unique elements
                Set<Integer> subsequenceSet = Arrays.stream(subsequenceElements).boxed().collect(Collectors.toSet());

                for (int j = 0; j < subsequenceLength && k < testSequenceLengthPerProcess; j++) {
                    int randomIndex = random.nextInt(0, amountOfSubsequenceElements);
                    int randomElement = subsequenceElements[randomIndex];

                    processTestSequence.add(new Recall(i, randomElement));

                    if (subsequenceSet.contains(randomElement)) {
                        subsequenceSet.remove(randomElement);
                        numberOfDifferentPagesPerProcess[i]++;
                    }

                    k++;
                }
            }
        }
    }

    private void createGlobalTestSequences() {
        Random random = new Random();

        while (!processesTestSequence.isEmpty()) {
            int randomProcessIndex = random.nextInt(0, processesTestSequence.size());
            Queue<Recall> randomProcess = processesTestSequence.get(randomProcessIndex);
            Recall recallToAdd = randomProcess.poll();

            globalTestSequence1.add(recallToAdd);

            try {
                globalTestSequence2.add(recallToAdd.clone());
                globalTestSequence3.add(recallToAdd.clone());
                globalTestSequence4.add(recallToAdd.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }

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

    private int generateAmountOfSubsequenceElements() {
        Random random = new Random();

        if (this.testSequenceLengthPerProcess <= 300)
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.03), (int) (this.testSequenceLengthPerProcess * 0.1));
        else if (this.testSequenceLengthPerProcess <= 3000)
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.01), (int) (this.testSequenceLengthPerProcess * 0.02));
        else
            return random.nextInt((int) (this.testSequenceLengthPerProcess * 0.0005), (int) (this.testSequenceLengthPerProcess * 0.002));
    }

    private int[] generateSubsequenceElements(int amount) {
        int[] subsequence = new int[amount];
        Random random = new Random();

        for (int i = 0; i < amount; i++)
            subsequence[i] = this.pages.remove(random.nextInt(0, this.pages.size()));

        return subsequence;
    }
}
