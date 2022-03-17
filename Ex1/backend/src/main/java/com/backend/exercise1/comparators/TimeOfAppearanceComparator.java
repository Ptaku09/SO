package com.backend.exercise1.comparators;

import com.backend.exercise1.Process;

import java.util.Comparator;

public class TimeOfAppearanceComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        return Integer.compare(p1.getTimeOfAppearance(), p2.getTimeOfAppearance());
    }
}
