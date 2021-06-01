package com.javainaction.chapter05;

import com.javainaction.entities.TradeTransaction;
import com.javainaction.entities.Trader;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Par05PuttingIntoPractice {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<TradeTransaction> transactions = Arrays.asList(
                new TradeTransaction(brian, 2011, 300),
                new TradeTransaction(raoul, 2012, 1000),
                new TradeTransaction(raoul, 2011, 400),
                new TradeTransaction(mario, 2012, 710),
                new TradeTransaction(mario, 2012, 700),
                new TradeTransaction(alan, 2012, 950)
        );

        // 1. Find all transactions in the year 2011 and sort them by value (small to high).
        log.info("Overview of all transactions in 2011 sorted ascending by value.");
        List<TradeTransaction> tranactions2011 = transactions.stream()
                .filter( (TradeTransaction transaction) -> transaction.getYear() == 2011 )
                .sorted(Comparator.comparing( (transaction1) -> transaction1.getValue()))
                .collect(Collectors.toList());
        tranactions2011.forEach( (TradeTransaction transaction) -> log.info("TradeTransaction: {}", transaction.toString()));

        // 2. What are all the unique cities where the traders work?
        log.info("2. What are all the unique cities where the traders work?");
        List<String> distinctTraderCities = transactions.stream()
                .map( (TradeTransaction transaction) -> transaction.getTrader().getCity())
                .distinct()
                .sorted()
                .collect(Collectors.toList())
                ;
        distinctTraderCities.forEach( (String cities) -> log.info("City: {}", cities));

        // 3. Find all traders from Cambridge and sort them by name.
        log.info("3. Find all traders from Cambridge and sort them by name.");
        List<Trader> traders = transactions.stream()
                .map( (TradeTransaction transaction) -> transaction.getTrader())
                .filter( (Trader trader) -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted( Comparator.comparing( trader -> trader.getName()))
                .collect(Collectors.toList());
        traders.forEach( (Trader trader) -> log.info("Trader {}", trader.getName()) );

        // 4. Return a string of all traders’ names sorted alphabetically.
        log.info("4. Return a string of all traders’ names sorted alphabetically.");
        String strTraderNames =  transactions.stream()
                .map( transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce( "", (String string1, String string2) -> string1 + " " + string2)
                .trim()
                ;
        log.info("String of traders names: {}", strTraderNames);

        // 5. Are any traders based in Milan?
        log.info("5. Are any traders based in Milan?");
        Optional<Trader> anyTrader = transactions.stream()
                .filter((TradeTransaction transaction) -> transaction.getTrader().getCity().equals("Milan"))
                .map( transaction -> transaction.getTrader())
                .findAny()
                ;
        log.info("Any trader in Milan? ()", anyTrader.isPresent());
        boolean presence = transactions.stream()
                .anyMatch( transaction -> transaction.getTrader().getCity().equals("Milan"))
                ;

        // 6. Print all transactions’ values from the traders living in Cambridge.
        log.info("6. Print all transactions’ values from the traders living in Cambridge.");
        transactions.stream()
                .filter( (TradeTransaction transaction) -> transaction.getTrader().getCity().equals("Cambridge"))
                .sorted(Comparator.comparing( (TradeTransaction transaction) -> transaction.getTrader().getName()))
                .forEach( (TradeTransaction transaction) -> log.info("Trader {} with value {}", transaction.getTrader().getName(), transaction.getValue()));

        // First option: multiple ways of using Stream.max().
        log.info("7. What’s the highest value of all the transactions?");
        final Optional<Integer> optionalMaxValue = transactions.stream()
                .map(TradeTransaction::getValue)
                .max(Comparator.comparing( Integer::intValue));
//                .max(Integer::compare);
        log.info("Maximum value of all transactions: {}", optionalMaxValue.orElse(0));

        // Second option: using Stream.reduce() with method reference.
        int maxValue = transactions.stream()
                .map(TradeTransaction::getValue)
                .reduce(0, Integer::max);
        log.info("Maximum value of all transactions: {}", maxValue);

        // Second option, using Stream.reduce() with a lambda.
        maxValue = transactions.stream()
                .map(TradeTransaction::getValue)
                .reduce(0, (x, y) -> x > y ? x : y)
                ;
        log.info("Maximum value of all transactions: {}", maxValue);

        // The best version because it avoids hidden boxing costs.
        OptionalInt optionalIntMaxValue = transactions.stream()
                .mapToInt(TradeTransaction::getValue)
                .max();
        log.info("Maximum value of all transactions: {}", optionalIntMaxValue.orElse(0));

        // You have to use optional here.
        // When using the non-optional version you have to use a seed (identifier) and
        // if you use '0' that would be the lowest number.
        log.info("8. Find the transaction with the smallest value.");
        Optional<Integer> minValue = transactions.stream()
                .map(TradeTransaction::getValue)
//                .reduce( 0, Integer::min);
                .min(Comparator.comparing(Integer::intValue));
        log.info("Minimum value of all transactions: {}", minValue.orElse(0));

    }
}
