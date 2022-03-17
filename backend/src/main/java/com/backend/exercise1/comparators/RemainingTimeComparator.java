package com.backend.exercise1.comparators;

import com.backend.exercise1.Process;

import java.util.Comparator;

public class RemainingTimeComparator implements Comparator<Process> {
    @Override
    public int compare(Process o1, Process o2) {
        return Integer.compare(o1.getRemainingTime(), o2.getRemainingTime());
    }
}
