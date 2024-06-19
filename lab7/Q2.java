package lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Q2 {
    public static void main(String[] args) {
        // sequential
        int val1 = 30;
        int val2 = 31;
        long startTime1, endTime1, startTime2, endTime2;

        System.out.println("Sequential Calculation");
        System.out.format("\tCalculating Fibonacci(%d)\n", val1);
        startTime1 = System.currentTimeMillis();
        int result1 = fib(val1);
        endTime1 = System.currentTimeMillis();
        System.out.format("\tFibonacci(%d) = %d\n", val1, result1);
        System.out.format("\tStart at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime1, endTime1, val1, endTime1 - startTime1);

        System.out.format("\tCalculating Fibonacci(%d)\n", val2);
        startTime2 = System.currentTimeMillis();
        int result2 = fib(val2);
        endTime2 = System.currentTimeMillis();
        System.out.format("\tFibonacci(%d) = %d\n", val2, result2);
        System.out.format("\tStart at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", startTime2, endTime2, val2, endTime2 - startTime2);

        System.out.format("Earlier Start: %d\t Later Finish: %d\n", startTime1, endTime2);
        System.out.format("\tTotal calculation time = %d milliseconds\n\n", endTime2 - startTime1);

        // asynchronous
        System.out.println("Asynchronous Calculation");
        List<CompletableFuture<StartAndEndTime>> futures = new ArrayList<>();
        futures.add(CompletableFuture.supplyAsync(() -> {
            System.out.format("\tCalculating Fibonacci(%d)\n", val1);
            long start = System.currentTimeMillis();
            long result = fib(val1);
            long end = System.currentTimeMillis();
            System.out.format("\tFibonacci(%d) = %d\n", val1, result);
            System.out.format("\tStart at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", start, end, val1, end - start);
            return new StartAndEndTime(start, end);
        }));
        futures.add(CompletableFuture.supplyAsync(() -> {
            System.out.format("\tCalculating Fibonacci(%d)\n", val2);
            long start = System.currentTimeMillis();
            long result = fib(val2);
            long end = System.currentTimeMillis();
            System.out.format("\tFibonacci(%d) = %d\n", val2, result);
            System.out.format("\tStart at %d\tFinish at %d\tCalculation time for Fibonacci(%d) = %d milliseconds\n", start, end, val2, end - start);
            return new StartAndEndTime(start, end);
        }));

        CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenAccept(__ -> {
                    long start = Long.MAX_VALUE;
                    long end = Long.MIN_VALUE;
                    for (CompletableFuture<StartAndEndTime> future : futures) {
                        try {
                            StartAndEndTime startAndEndTime = future.get();
                            start = Math.min(start, startAndEndTime.startTime);
                            end = Math.max(end, startAndEndTime.endTime);

                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.format("Earlier Start: %d\t Later Finish: %d\n", start, end);
                    System.out.format("\tTotal calculation time = %d milliseconds\n\n", end - start);
                }).join();
    }

    public static int fib(int n) {
        if (n == 0) return 0;
        if (n <= 2) return 1;
        return fib(n - 1) + fib(n - 2);
    }
}

class StartAndEndTime {
    long startTime, endTime;

    public StartAndEndTime(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
