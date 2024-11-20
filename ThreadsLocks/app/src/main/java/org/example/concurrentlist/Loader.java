package org.example.concurrentlist;

import java.util.Random;

public class Loader extends Thread {
    private final ConcurrentSortedList list;
    Loader(ConcurrentSortedList list) {
        this.list = list;
    }
    public void run() {
        for (int i = 0; i < 4; ++i) {
            list.insert(new Random().nextInt(1000));
        }
    }
}
