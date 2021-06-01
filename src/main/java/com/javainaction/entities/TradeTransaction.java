package com.javainaction.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class TradeTransaction {
    private Trader trader;
    private int year;
    private int value;

//    public TradeTransaction(Trader trader, int year, int value) {
//        this.trader = trader;
//        this.year = year;
//        this.value = value;
//    }
//
//    public int getYear() {
//        return year;
//    }
//
//    public Trader getTrader() {
//        return trader;
//    }
//
    public int getValue() {
        return value;
    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        TradeTransaction that = (TradeTransaction) o;
//        return year == that.year && value == that.value && trader.equals(that.trader);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(trader, year, value);
//    }
//
//    @Override
//    public String toString() {
//        return "TradeTransaction{" +
//                "trader=" + trader +
//                ", year=" + year +
//                ", value=" + value +
//                '}';
//    }
}
