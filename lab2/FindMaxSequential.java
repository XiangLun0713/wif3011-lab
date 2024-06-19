package lab2;

import java.util.concurrent.Callable;

public class FindMaxSequential implements Callable<Integer> {
    private final int[] array;
    private int localMax;
    private final int startIndexInclusive, endIndexExclusive;

    public FindMaxSequential(int[] array) {
        this.array = array;
        this.startIndexInclusive = 0;
        this.endIndexExclusive = array.length;
    }

    public FindMaxSequential(int[] array, int startIndexInclusive, int endIndexExclusive) {
        this.array = array;
        this.startIndexInclusive = startIndexInclusive;
        this.endIndexExclusive = endIndexExclusive;
        this.localMax = Integer.MIN_VALUE;
    }

    @Override
    public Integer call() {
        for (int i = startIndexInclusive; i < endIndexExclusive; i++) {
            if (array[i] > localMax) localMax = array[i];
        }
        return localMax;
    }
}
