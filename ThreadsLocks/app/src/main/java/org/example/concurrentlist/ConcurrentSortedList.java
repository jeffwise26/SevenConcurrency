package org.example.concurrentlist;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentSortedList {
    private class Node {
        int value;
        Node previous;
        Node next;
        private ReentrantLock lock = new ReentrantLock();

        Node(int value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }

        Node() {}
    }

    private final Node head;
    private final Node tail;

    public ConcurrentSortedList() {
        head = new Node();
        tail = new Node();
        head.next = tail;
        tail.previous = head;
    }

    public void insert(int value) {
       Node current = head;
       current.lock.lock();
       Node next = current.next;
       try {
          while(true) {
              next.lock.lock();
             try {
                if (next == tail || next.value < value) {
                    Node node = new Node(value, current, next);
                    next.previous = node;
                    current.next = node;
                    return;
                }
             } finally {
                 current.lock.unlock();
             }
             current = next;
             next = current.next;
          }
       } finally {
           next.lock.unlock();
       }
    }

    public void print() {
        Node current = head;
        while(current.next != tail) {
            System.out.println(current.next.value);
            current = current.next;
        }
    }
}
