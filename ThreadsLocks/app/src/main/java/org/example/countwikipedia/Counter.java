package org.example.countwikipedia;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public class Counter implements Runnable {
    private BlockingQueue<Page> queue;
    private HashMap<String, Integer> count;

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
        count.merge(word, 1, Integer::sum);
    }

}
