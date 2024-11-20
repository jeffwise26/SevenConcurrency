package org.example.philosopher;

import java.util.Random;

public class Philosopher extends Thread {
    private final String name;
    private final Chopstick first;
    private final Chopstick second;
    private final Random random;

    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        if (left.getId() < right.getId()) {
          first= left; second = right;
        } else {
           first = right; second = left;
        }
        random = new Random();
    }

    public void run() {
        try {
            while (true) {
                System.out.println(name + " think");
                Thread.sleep(random.nextInt(1000));
                synchronized (first) {
                    System.out.println(name + " first");
                    synchronized (second) {
                        System.out.println(name + " second");
                        Thread.sleep(random.nextInt(1000));
                        System.out.println(name + " done eating");
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
