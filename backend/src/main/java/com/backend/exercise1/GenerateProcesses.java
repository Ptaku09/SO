package com.backend.exercise1;

import org.apache.commons.math3.distribution.BetaDistribution;

import java.util.ArrayList;

public class GenerateProcesses {
    public static ArrayList<Process> generateProcesses(int amount, double alpha, double beta) {
        BetaDistribution durationDistribution = new BetaDistribution(alpha, beta); //2, 8 ---> beta
        BetaDistribution appearanceDistribution = new BetaDistribution(1, 4);
        ArrayList<Process> processes = new ArrayList<>();

        for (int i = 0; i < amount; i++)
            processes.add(new Process(i + 1, (int) Math.round(durationDistribution.sample() * 1000) + 1, (int) Math.round(appearanceDistribution.sample() * 1000)));

        return processes;
    }
}
