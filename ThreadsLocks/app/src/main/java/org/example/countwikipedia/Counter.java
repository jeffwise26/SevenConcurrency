package org.example.countwikipedia;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter implements Runnable {
    private final BlockingQueue<Page> queue;
    private final HashMap<String, Integer> count;
    private static final Lock lock = new ReentrantLock();

    public Counter(BlockingQueue<Page> queue, HashMap<String, Integer> count) {
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
                    countWord(word);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void countWord(String word) {
        lock.lock();
        try {
            count.merge(word, 1, Integer::sum);
        } finally {
            lock.unlock();
        }
    }

}
