package com.backend.exercise5;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Manager {
    List<CPU> processors;
    Queue<Process> processesQueue;

    public Manager(int cpusAmount, int maxLoad, int minLoad, int tries) {
        processors = new ArrayList<>();
        processesQueue = new LinkedList<>();
    }

    public List<Results> runSimulation() {
        return null;
    }
}
