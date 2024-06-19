package lab2;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class Q4 {
    public static void main(String[] args) {
        int[] array = new int[1_000_000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1, 50_001);
        }
        Long start, end;

        // run sequential
        try {
            start = System.nanoTime();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<Integer> future = executor.submit(new FindMaxSequential(array));
            int max = future.get();
            executor.shutdown();
            end = System.nanoTime();
            System.out.format("Sequential Max is %d\n", max);
            System.out.format("Time taken for sequential is %f ms\n", (end - start) / 10e6);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // run concurrent
        start = System.nanoTime();
        AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
        new FindMaxConcurrentDriver(array, 4, max).run();
        end = System.nanoTime();
        System.out.format("Concurrent max is %d\n", max.get());
        System.out.format("Time taken for concurrent with 4 threads is %f ms\n", (end - start) / 10e6);
    }
}
