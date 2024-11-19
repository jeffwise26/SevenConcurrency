package org.example.download;

import java.util.Objects;

public class StdOutProgressListener extends Thread implements ProgressListener {
    private final String name;
    private final int delay;

    StdOutProgressListener(String name, Integer delay) {
        this.name = name;
        this.delay = Objects.requireNonNullElse(delay, 0);
    }

    public void onProgress(Double current, Double total) throws InterruptedException {
        Thread.sleep(delay);
        if (total == 0) {
            System.out.printf("%s current: %.0f%n", name, current);
        } else {
            Double percentage = (current / total) * 100;
            System.out.printf("%s current: %.0f percent: %.0f%n", name, current, percentage);
        }
    }
}
