package lab6;

public class PeekStack implements Runnable {

    private final StackAccess<Integer> stack;
    private final int numOfOperation;

    public PeekStack(StackAccess<Integer> stack, int numOfOperation) {
        this.stack = stack;
        this.numOfOperation = numOfOperation;
    }

    @Override
    public void run() {
        // run 4 times
        for (int i = 0; i < numOfOperation; i++) {
            stack.peek();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
