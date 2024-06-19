package lab1;

import java.util.concurrent.atomic.AtomicBoolean;

public class Q2 {
    public static void main(String[] args) {
        Object lock = new Object();
        AtomicBoolean isPrintCharTurn = new AtomicBoolean(true);

        Thread thread1 = new Thread(new PrintCharQ2('A', 10, lock, isPrintCharTurn));
        Thread thread2 = new Thread(new PrintNumQ2(45, lock, isPrintCharTurn, thread1));

        thread1.start();
        thread2.start();
    }
}

class PrintCharQ2 implements Runnable {
    private final char c;
    private final int time;
    private final Object lock;
    private final AtomicBoolean isPrintCharTurn;

    public PrintCharQ2(char c, int time, Object lock, AtomicBoolean isPrintCharTurn) {
        this.c = c;
        this.time = time;
        this.lock = lock;
        this.isPrintCharTurn = isPrintCharTurn;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < time; i++) {
                while (!isPrintCharTurn.get()) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(c);
                isPrintCharTurn.set(false);
                lock.notifyAll();
            }
        }
    }
}

class PrintNumQ2 implements Runnable {
    private final int num;
    private final Object lock;
    private final AtomicBoolean isPrintCharTurn;
    private final Thread printCharThread;

    public PrintNumQ2(int num, Object lock, AtomicBoolean isPrintCharTurn, Thread printCharThread) {
        this.num = num;
        this.lock = lock;
        this.isPrintCharTurn = isPrintCharTurn;
        this.printCharThread = printCharThread;
    }

    @Override
    public void run() {
        synchronized (lock) {
            for (int i = 0; i < num; i++) {
                while (isPrintCharTurn.get() && !printCharThread.getState().equals(Thread.State.TERMINATED)) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(i);
                isPrintCharTurn.set(true);
                lock.notifyAll();
            }
        }
    }
}
