package org.example;

public class Chopstick {
    static int globalIndex = 0;
    private final int id;

    private synchronized int getGlobalIndex() {
       return globalIndex;
    }
    private synchronized void incrementGlobalIndex() {
        ++globalIndex;
    }
    public int getId() {
        return this.id;
    }

    public Chopstick() {
       this.id = getGlobalIndex();
       incrementGlobalIndex();
    }
}
