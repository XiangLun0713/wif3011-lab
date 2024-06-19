package lab6;

import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StackAccess<T> {
    private final static int MAX_SIZE = 3;

    private final Stack<T> stack;
    private final Lock lock;
    private final Condition stackChanged;

    public StackAccess() {
        this.stack = new Stack<>();
        this.lock = new ReentrantLock();
        this.stackChanged = lock.newCondition();
    }

    public void pop() {
        lock.lock();
        try {
            while (stack.isEmpty()) {
                // stack is empty, wait
                boolean isAwaken = stackChanged.await(1, TimeUnit.SECONDS);
                if (!isAwaken) {
                    // discard operation if time elapsed
                    System.out.println("[Time Out] Failed to read value");
                    return;
                }
            }
            T readValue = stack.pop();
            System.out.println("[Success] Read value: " + readValue);
            stackChanged.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void push(T val) {
        lock.lock();
        try {
            if (stack.size() >= MAX_SIZE) {
                // stack is full, wait
                boolean isAwaken = stackChanged.await(1, TimeUnit.SECONDS);
                if (!isAwaken) {
                    // discard operation if time elapsed
                    System.out.println("[Time Out] Failed to insert number " + val);
                    return;
                }
            }
            stack.push(val);
            System.out.println("[Success] Inserted value: " + val);
            stackChanged.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public void peek() {
        lock.lock();
        try {
            while (stack.isEmpty()) {
                // stack is empty, waits
                boolean isAwaken = stackChanged.await(1, TimeUnit.SECONDS);
                if (!isAwaken) {
                    // discard operation if time elapsed
                    System.out.println("[Time Out] Failed to peek value");
                    return;
                }
            }
            T peekedValue = stack.peek();
            System.out.println("[Success] Peeked value: " + peekedValue);
            stackChanged.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
