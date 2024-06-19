package lab2;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class FindMaxConcurrentDriver {
    private final int[] array;
    private final int numOfThreads;
    private final AtomicInteger max;

    public FindMaxConcurrentDriver(int[] array, int numOfThreads, AtomicInteger max) {
        this.array = array;
        this.numOfThreads = numOfThreads;
        this.max = max;
    }

    public void run() {
        ExecutorService executor = Executors.newFixedThreadPool(numOfThreads);
        List<Callable<Integer>> callables = new LinkedList<>();
        for (int i = 0; i < numOfThreads; i++) {
            int start = (int) (array.length * 1.0 / numOfThreads * i);
            int end = (int) ((array.length * 1.0 / numOfThreads) * (1 + i));
            callables.add(new FindMaxSequential(array, start, end));
        }
        try {
            List<Future<Integer>> futures = executor.invokeAll(callables);
            for (var future : futures) {
                int localMax = future.get();
                if (max.get() < localMax) max.set(localMax);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }
}
