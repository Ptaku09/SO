package com.backend.exercise4;

public class Recall {
    private final int processNumber;
    private final int pageNumber;

    public Recall(int processNumber, int pageNumber) {
        this.processNumber = processNumber;
        this.pageNumber = pageNumber;
    }

    public int getProcessNumber() {
        return processNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }
}
