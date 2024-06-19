package lab6;

import java.util.Random;

public class WriteStack implements Runnable {

    private final StackAccess<Integer> stack;
    private final int numOfOperation;

    public WriteStack(StackAccess<Integer> stack, int numOfOperation) {
        this.stack = stack;
        this.numOfOperation = numOfOperation;
    }

    @Override
    public void run() {
        Random random = new Random();
        // run 4 times
        for (int i = 0; i < numOfOperation; i++) {
            stack.push(random.nextInt(100));
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
