package lab10;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Q1 {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinTask<Long> forkJoinTask = forkJoinPool.submit(new SumFactorialQ1(1, 21));
        try {
            long sum = forkJoinTask.get();
            System.out.println("The sum of all factorials for numbers 1 to 20 is " + sum);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

class SumFactorialQ1 extends RecursiveTask<Long> {

    private final int startInclusive, endExclusive;
    private final static int SMALLEST_RANGE = 5;

    public SumFactorialQ1(int startInclusive, int endExclusive) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
    }

    @Override
    protected Long compute() {
        if (endExclusive - startInclusive > SMALLEST_RANGE) {
            int mid = (startInclusive + endExclusive) / 2;

            SumFactorialQ1 sumFactorialQ1_1 = new SumFactorialQ1(startInclusive, mid);
            SumFactorialQ1 sumFactorialQ1_2 = new SumFactorialQ1(mid, endExclusive);

            sumFactorialQ1_1.fork();
            sumFactorialQ1_2.fork();

            return sumFactorialQ1_1.join() + sumFactorialQ1_2.join();

        } else {
            long sum = 0;
            for (int i = startInclusive; i < endExclusive; i++) {
                sum += factorial(i);
            }
            return sum;
        }
    }

    private long factorial(int n) {
        if (n == 0) return 1;

        return n * factorial(n - 1);
    }
}