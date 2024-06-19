package lab10;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.atomic.AtomicLong;

public class Q3 {
    public static void main(String[] args) {
        int startInclusive = 1, endExclusive = 21;
        long startTime, endTime;
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();

        // Sequential
        startTime = System.nanoTime();
        long sum = 0;
        for (int i = startInclusive; i < endExclusive; i++) {
            sum += factorial(i);
        }
        endTime = System.nanoTime();
        System.out.format("Result for sequential is %d\nTime taken is %d ns\n\n", sum, endTime - startTime);

        // SumFactorialQ1
        startTime = System.nanoTime();
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new SumFactorialQ1(1, 21));
        try {
            sum = forkJoinTask.get();
            endTime = System.nanoTime();
            System.out.format("Result for SumFactorialQ1 is %d\nTime taken is %d ns\n\n", sum, endTime - startTime);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        // SumFactorialQ1
        startTime = System.nanoTime();
        AtomicLong atomicSum = new AtomicLong(0);
        forkJoinPool.invoke(new SumFactorialQ2(1, 21, atomicSum));
        sum = atomicSum.get();
        endTime = System.nanoTime();
        System.out.format("Result for SumFactorialQ2 is %d\nTime taken is %d ns\n\n", sum, endTime - startTime);
    }

    private static long factorial(int n) {
        if (n == 0) return 1;

        return n * factorial(n - 1);
    }
}
