package org.example.concurrentlist;

import java.io.IOException;

public class Runner {
    public static void main(String[] args) throws IOException, InterruptedException {
        ConcurrentSortedList list = new ConcurrentSortedList();
        Loader l1 = new Loader(list);
        Loader l2 = new Loader(list);
        l1.start();
        l2.start();
        l1.join();
        l2.join();

        list.print();
    }
}
