package org.example.countwikipedia;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {
    private BlockingQueue<Page> queue;

    public Parser(BlockingQueue<Page> queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        Iterable<Page> pages = Pages.getPages();
        for (Page page: pages) {
            try {
                queue.put(page);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
