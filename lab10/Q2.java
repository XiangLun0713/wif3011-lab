package lab10;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.atomic.AtomicLong;

public class Q2 {
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        AtomicLong sum = new AtomicLong(0);
        forkJoinPool.invoke(new SumFactorialQ2(1, 21, sum));
        System.out.println("The sum of all factorials for numbers 1 to 20 is " + sum.get());
    }
}

class SumFactorialQ2 extends RecursiveAction {

    private final AtomicLong sum;
    private final int startInclusive, endExclusive;
    private final static int SMALLEST_RANGE = 5;

    public SumFactorialQ2(int startInclusive, int endExclusive, AtomicLong sum) {
        this.startInclusive = startInclusive;
        this.endExclusive = endExclusive;
        this.sum = sum;
    }

    @Override
    protected void compute() {
        if (endExclusive - startInclusive > SMALLEST_RANGE) {
            int mid = (startInclusive + endExclusive) / 2;

            SumFactorialQ2 sumFactorialQ2_1 = new SumFactorialQ2(startInclusive, mid, sum);
            SumFactorialQ2 sumFactorialQ2_2 = new SumFactorialQ2(mid, endExclusive, sum);

            sumFactorialQ2_1.fork();
            sumFactorialQ2_2.fork();

            sumFactorialQ2_1.join();
            sumFactorialQ2_2.join();

        } else {
            for (int i = startInclusive; i < endExclusive; i++) {
                sum.getAndAdd(factorial(i));
            }
        }
    }

    private long factorial(int n) {
        if (n == 0) return 1;

        return n * factorial(n - 1);
    }
}