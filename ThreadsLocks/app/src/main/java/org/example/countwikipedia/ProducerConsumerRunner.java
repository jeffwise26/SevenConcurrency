package org.example.countwikipedia;

import java.util.HashMap;
import java.util.concurrent.*;

public class ProducerConsumerRunner {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Page> queue = new ArrayBlockingQueue<>(100);
        HashMap<String, Integer> count = new HashMap<>();

        try (ExecutorService executor = Executors.newCachedThreadPool()) {
            int MAX_THREADS = 2;
            for (int i = 0; i < MAX_THREADS; ++i) {
                executor.execute(new Counter(queue, count));
            }

            Thread parser = new Thread(new Parser(queue));
            parser.start();
            parser.join();
            for (int i = 0; i < MAX_THREADS; ++i) {
                queue.put(new Page(null, true));
            }
            executor.shutdown();
            executor.awaitTermination(1L, TimeUnit.MINUTES);
            System.out.println(count);
        }
    }
}
