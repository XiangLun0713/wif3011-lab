package lab5;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class BankingQueue {
    static volatile Queue<Integer> queue = new LinkedList<>();

    public static void main(String[] args) {
        Random random = new Random();
        int num = random.nextInt(1, 11);
        System.out.println("Ah, my customer number is " + num);
        new Thread(new CallingQueue(queue)).start();
        new Thread(new CustomerInLine(queue, num)).start();
    }
}

class CallingQueue implements Runnable {

    volatile Queue<Integer> queue;

    public CallingQueue(Queue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.println("Calling for the customer #" + i);
            queue.add(i);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.exit(0);
    }
}

class CustomerInLine implements Runnable {

    volatile Queue<Integer> queue;
    int target;

    public CustomerInLine(Queue<Integer> queue, int num) {
        this.queue = queue;
        this.target = num;
    }

    @Override
    public void run() {
        while (true) {
            // wait until their customer number is called
            if (!queue.isEmpty()) {
                int num = queue.poll();
                if (num == target) {
                    System.out.format("Great, finally #%d was called, now it is my turn\n", target);
                }
            }
        }
    }
}
