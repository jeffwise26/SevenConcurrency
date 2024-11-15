package org.example;

public class Chopstick {
    static int globalIndex = 0;
    private final int id;

    private synchronized void incrementGlobalIndex() {
        ++globalIndex;
    }
    
    public synchronized int getId() {
        return this.id;
    }

    public Chopstick() {
       this.id = getId();
       incrementGlobalIndex();
    }
}
