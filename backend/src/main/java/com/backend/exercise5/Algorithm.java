package com.backend.exercise5;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public abstract class Algorithm {
    protected final String algorithmName;
    protected final List<CPU> processors;
    protected final Queue<Process> processesQueue;
    protected final List<Double> averageLoadCheckPoints;
    protected final List<Double> averageDeviationCheckPoints;
    protected final int maxLoad;
    protected double averageLoad;
    protected double averageDeviation;
    protected int loadQuestions;
    protected int processMovements;

    public Algorithm(String algorithmName, List<CPU> processors, Queue<Process> processesQueue, int maxLoad) {
        this.algorithmName = algorithmName;
        this.processors = processors;
        this.processesQueue = processesQueue;
        this.averageLoadCheckPoints = new ArrayList<>();
        this.averageDeviationCheckPoints = new ArrayList<>();
        this.maxLoad = maxLoad;
        this.loadQuestions = 0;
        this.processMovements = 0;
    }

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
            giveHelp();
            updateProcessors();
        }

        calculateAverageLoad();
        calculateAverageDeviation();

        return new Results(algorithmName, averageLoad, averageDeviation, loadQuestions, processMovements);
    }

    protected abstract int findProcessor(int cpuNumber);

    protected abstract void giveHelp();

    protected void updateProcessors() {
        for (CPU processor : processors)
            processor.updateProcesses();
    }

    protected void calculateAverageLoadCheckPoint() {
        processors
                .stream()
                .map(CPU::getLoad)
                .mapToInt(Integer::intValue)
                .average()
                .ifPresent(averageLoadCheckPoints::add);
    }

    protected void calculateAverageLoad() {
        this.averageLoad = averageLoadCheckPoints
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        this.averageLoad = Math.round(this.averageLoad * 100) / 100.0;
    }

    protected void calculateAverageDeviationCheckPoint() {
        double average = averageLoadCheckPoints.get(averageLoadCheckPoints.size() - 1);
        double sum = processors
                .stream()
                .map(cpu -> Math.pow(cpu.getLoad() - average, 2))
                .reduce(0.0, Double::sum);

        averageDeviationCheckPoints.add(Math.sqrt(sum / processors.size()));
    }

    protected void calculateAverageDeviation() {
        this.averageDeviation = averageDeviationCheckPoints
                .stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        this.averageDeviation = Math.round(this.averageDeviation * 100) / 100.0;
    }
}
