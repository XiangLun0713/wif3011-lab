package lab2;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Q2 {
    public static void main(String[] args) {
        int[] array = new int[1_000_000];
        Random random = new Random();
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(1, 50_001);
        }
        AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
        new FindMaxConcurrentDriver(array, 100, max).run();
        System.out.println("Max is " + max.get());
    }
}

