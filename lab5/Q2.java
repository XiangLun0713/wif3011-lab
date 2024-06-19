package lab5;

import java.util.concurrent.atomic.AtomicInteger;

public class Q2 {
    static AtomicInteger integer = new AtomicInteger(0);

    public static void main(String[] args) {
        new Thread(new IncrementTask(integer)).start();
        new Thread(new CheckTask(integer)).start();
    }
}

class IncrementTask implements Runnable {
    AtomicInteger integer;

    public IncrementTask(AtomicInteger integer) {
        this.integer = integer;
    }

    @Override
    public void run() {
        while (true) {
            int curr = integer.get() + 1;
            System.out.println("Counter incremented: " + curr);
            integer.set(curr);
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class CheckTask implements Runnable {
    AtomicInteger integer;
    int prev;

    public CheckTask(AtomicInteger integer) {
        this.integer = integer;
        prev = integer.get();
    }

    @Override
    public void run() {
        while (true) {
            int curr = integer.get();
            if (curr != prev) {
                prev = curr;
                System.out.println("Counter changed: " + curr);
            }
            if (curr == 5000) System.exit(0);
        }
    }
}

