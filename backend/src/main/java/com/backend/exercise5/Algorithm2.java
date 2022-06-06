package com.backend.exercise5;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Algorithm2 implements Runnable {
    private final List<CPU> processors;
    private final Queue<Process> processesQueue;
    private final List<Double> averageLoadCheckPoints;
    private final List<Double> averageDeviationCheckPoints;
    private final int maxLoad;
    private double averageLoad;
    private double averageDeviation;
    private int loadQuestions;
    private int processMovements;

    public Algorithm2(List<CPU> processors, Queue<Process> processesQueue, int maxLoad) {
        this.processors = processors;
        this.processesQueue = processesQueue;
        this.averageLoadCheckPoints = new ArrayList<>();
        this.averageDeviationCheckPoints = new ArrayList<>();
        this.maxLoad = maxLoad;
        this.loadQuestions = 0;
        this.processMovements = 0;
    }

    @Override
    public Results run() {
        int averageLoadCounter = 1;

        while (!processesQueue.isEmpty() || !processors.stream().allMatch(cpu -> cpu.getActiveProcesses().isEmpty())) {
            Process currentProcess = processesQueue.poll();

            if (currentProcess != null) {
                int cpuNumber = currentProcess.getCpuNumber();
                int foundedProcessor = findProcessor(cpuNumber);

                processors.get(foundedProcessor).addProcess(currentProcess);
            }

            if (averageLoadCounter % 50 == 0) {
                calculateAverageLoadCheckPoint();
                calculateAverageDeviationCheckPoint();
                averageLoadCounter = 0;
            }

            averageLoadCounter++;
            updateProcessors();
        }

        calculateAverageLoad();
        calculateAverageDeviation();

        return new Results("Algorithm II", averageLoad, averageDeviation, loadQuestions, processMovements);
    }

    private void updateProcessors() {
        for (CPU processor : processors)
            processor.updateProcesses();
    }

    private int findProcessor(int cpuNumber) {
        Random random = new Random();
        this.loadQuestions++;

        if (processors.get(cpuNumber).getLoad() < maxLoad)
            return cpuNumber;
        else {
            int randomProcessor = random.nextInt(processors.size());

            while (processors.get(randomProcessor).getLoad() >= maxLoad || randomProcessor == cpuNumber) {
                this.loadQuestions++;
                randomProcessor = random.nextInt(processors.size());
                updateProcessors();
            }

            this.loadQuestions++;
            this.processMovements++;

            return randomProcessor;
        }
    }

    private void calculateAverageLoadCheckPoint() {
        processors
                .stream()
                .map(CPU::getLoad)
                .mapToInt(Integer::intValue)
                .average()
                .ifPresent(averageLoadCheckPoints::add);
    }

    private void calculateAverageLoad() {
        this.averageLoad = averageLoadCheckPoints
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        this.averageLoad = Math.round(this.averageLoad * 100) / 100.0;
    }

    private void calculateAverageDeviationCheckPoint() {
        double average = averageLoadCheckPoints.get(averageLoadCheckPoints.size() - 1);
        double sum = processors
                .stream()
                .map(cpu -> Math.pow(cpu.getLoad() - average, 2))
                .reduce(0.0, Double::sum);

        averageDeviationCheckPoints.add(Math.sqrt(sum / processors.size()));
    }

    private void calculateAverageDeviation() {
        this.averageDeviation = averageDeviationCheckPoints
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        this.averageDeviation = Math.round(this.averageDeviation * 100) / 100.0;
    }
}
