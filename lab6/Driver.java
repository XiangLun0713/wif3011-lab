package lab6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Driver {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        StackAccess<Integer> stackAccess = new StackAccess<>();
        executor.submit(new ReadStack(stackAccess, 10));
        executor.submit(new WriteStack(stackAccess, 4));
        executor.submit(new PeekStack(stackAccess, 10));
        executor.shutdown();
    }
}
