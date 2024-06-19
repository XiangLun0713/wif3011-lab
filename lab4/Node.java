package lab4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Node<T> {
    private T value;
    private final Lock lock = new ReentrantLock();
    private final Condition valueChanged = lock.newCondition();

    public void setValue(T value) {
        lock.lock();
        try {
            this.value = value;
            System.out.println("setting value to " + value);
            valueChanged.signalAll();
         } finally {
            lock.unlock();

        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void executeOnValue(T desiredValue, Runnable task) {
        lock.lock();
        try {
            while (!desiredValue.equals(this.value)) {
                valueChanged.await();
            }
            task.run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }
}
