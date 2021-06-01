package com.javainaction.utilities;

import lombok.Data;

import java.util.function.IntConsumer;

// https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html
@Data
public class Averager implements IntConsumer {

    private int total = 0;
    private int count = 0;

    public Averager() {

    }

    public double getAverage() {
        return count > 0 ? ( (double) total / count) : 0;
    }

    // This method can be used when interface BiConsumer is required.
    @Override
    public void accept(int value) {
        total += value;
        count++;
    }

    // This method can be used when interface BiConsumer is required.
    public void combine(Averager other) {
        total += other.total;
        count += other.count;
    }
}
