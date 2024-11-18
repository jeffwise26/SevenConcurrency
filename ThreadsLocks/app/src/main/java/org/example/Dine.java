package org.example;

public class Dine {
    public static void main(String[] args) {
        Chopstick one = new Chopstick();
        Chopstick two = new Chopstick();
        Chopstick three = new Chopstick();
        Chopstick four = new Chopstick();
        Chopstick five = new Chopstick();

        Philosopher p1 = new Philosopher("p1", one, two);
        Philosopher p2 = new Philosopher("p2", two, three);
        Philosopher p3 = new Philosopher("p3", three, four);
        Philosopher p4 = new Philosopher("p4", four, five);
        Philosopher p5 = new Philosopher("p5", five, one);

        p1.start();
        p2.start();
        p3.start();
        p4.start();
        p5.start();
    }
}
