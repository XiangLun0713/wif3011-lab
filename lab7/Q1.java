package lab7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Q1 {

    private static final Random random = new Random();

    public static void main(String[] args) {

        int start = 1;
        int end = random.nextInt(1, 111112);
        int n = (int) Math.ceil(end / 1000.0);

        List<CompletableFuture<NumAndDivisorCount>> futures = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            int finalI = i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                NumAndDivisorCount numAndDivisorCount = new NumAndDivisorCount();
                for (int j = finalI * (1000); j < Math.min(finalI * (1000) + 1000, end); j++) {
                    int count = 0;
                    for (int k = 1; k <= j; k++) {
                        if (j % k == 0) {
                            count++;
                        }
                    }
                    if (count > numAndDivisorCount.divisorCount) {
                        numAndDivisorCount.divisorCount = count;
                        numAndDivisorCount.num = j;
                    }
                }
                return numAndDivisorCount;
            }));
        }

        CompletableFuture<Void> completableFuture = CompletableFuture
                .allOf(futures.toArray(new CompletableFuture[0]))
                .thenAccept(__ -> {
                    NumAndDivisorCount max = new NumAndDivisorCount();
                    for (CompletableFuture<NumAndDivisorCount> future : futures) {
                        try {
                            NumAndDivisorCount curr = future.get();
                            if (curr.divisorCount > max.divisorCount) {
                                max = curr;
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.format("In the range of %d to %d, %d has the most divisor count of %d\n", start, end, max.num, max.divisorCount);
                });

        completableFuture.join();

    }
}

class NumAndDivisorCount {
    int num;
    int divisorCount;
}