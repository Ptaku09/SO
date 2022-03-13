package com.exercise1.backend.comparators;

import com.exercise1.backend.Process;

import java.util.Comparator;

public class RemainingTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return Integer.compare(o1.getRemainingTime(), o2.getRemainingTime());
    }
}
