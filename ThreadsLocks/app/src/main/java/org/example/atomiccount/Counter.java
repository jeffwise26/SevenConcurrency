package org.example.atomiccount;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger();

        class CounterThread extends Thread {
            public void run() {
                for (int i = 0; i < 10000; ++i) {
                    count.incrementAndGet();
                }
            }
        }

        CounterThread c1 = new CounterThread();
        CounterThread c2 = new CounterThread();
        c1.start();
        c2.start();

        c1.join();
        c2.join();

        System.out.println(count);
    }



}
