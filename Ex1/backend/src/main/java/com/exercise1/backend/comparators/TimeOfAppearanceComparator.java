package com.exercise1.backend.comparators;

import com.exercise1.backend.Process;

import java.util.Comparator;

public class TimeOfAppearanceComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        return Integer.compare(p1.getTimeOfAppearance(), p2.getTimeOfAppearance());
    }
}
