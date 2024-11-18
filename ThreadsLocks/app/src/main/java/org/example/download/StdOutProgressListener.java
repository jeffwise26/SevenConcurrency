package org.example.download;

public class StdOutProgressListener implements ProgressListener {
    private final String name;

    StdOutProgressListener(String name) {
        this.name = name;
    }

    public void onProgress(Double current, Double total) {
        if (total == 0) {
            System.out.printf("%s current: %.0f%n", name, current);
        } else {
            Double percentage = (current / total) * 100;
            System.out.printf("%s current: %.0f percent: %.0f%n", name, current, percentage);
        }
    }
}
