package lab9;

import java.util.stream.IntStream;

public class Q2 {
    public static void main(String[] args) {
        IntStream.range(1, 51)
                .parallel()
                .filter(Q2::isPrimeNumber)
                .forEach(i -> {
                    System.out.println("Thread " + Thread.currentThread().getName() + " processed number " + i);
                });
    }

    private static boolean isPrimeNumber(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}
