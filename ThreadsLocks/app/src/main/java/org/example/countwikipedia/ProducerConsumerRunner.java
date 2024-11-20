package org.example.countwikipedia;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumerRunner {

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Page> queue = new ArrayBlockingQueue<>(100);
        HashMap<String, Integer> count = new HashMap<>();
        Parser parser = new Parser(queue);
        Counter counter = new Counter(queue, count);

        Thread t1 = new Thread(parser);
        Thread t2 = new Thread(counter);
        t1.start();
        t2.start();
        t1.join();
        queue.put(new Page(null, true));
        t2.join();
        System.out.println(count);
    }
}
