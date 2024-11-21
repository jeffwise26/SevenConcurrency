package org.example.countwikipedia;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class Counter implements Runnable {
    private final BlockingQueue<Page> queue;
    private final ConcurrentHashMap<String, Integer> count;
    private final HashMap<String, Integer> localCount = new HashMap<>();

    public Counter(BlockingQueue<Page> queue, ConcurrentHashMap<String, Integer> count) {
        this.queue = queue;
        this.count = count;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Page page = queue.take();
                if (page.isPoisonPill()) {
                    break;
                }
                Iterable<String> words = page.getWords();
                for (String word : words) {
                    localCount.merge(word, 1, Integer::sum);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        merge();
    }

    public void merge() {
        for (Map.Entry<String, Integer> entry : localCount.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();

            while (true) {
                Integer current = count.get(key);
                if (current == null) {
                    if (count.putIfAbsent(key, value) == null) {
                        break;
                    }
                } else if (count.replace(key, current, current + value)) {
                    break;
                }
            }
        }
    }
}
