package com.javainaction.chapter06;

import com.javainaction.enums.Currency;
import com.javainaction.entities.FinancialTransaction;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Par01GroupingTransactions {

    private static final List<FinancialTransaction> transactions = Arrays.asList(new FinancialTransaction(Currency.EUR, 1500.0),
            new FinancialTransaction(Currency.USD, 2300.0),
            new FinancialTransaction(Currency.GBP, 9900.0),
            new FinancialTransaction(Currency.EUR, 1100.0),
            new FinancialTransaction(Currency.JPY, 7800.0),
            new FinancialTransaction(Currency.CHF, 6700.0),
            new FinancialTransaction(Currency.EUR, 5600.0),
            new FinancialTransaction(Currency.USD, 4500.0),
            new FinancialTransaction(Currency.CHF, 3400.0),
            new FinancialTransaction(Currency.GBP, 3200.0),
            new FinancialTransaction(Currency.USD, 4600.0),
            new FinancialTransaction(Currency.JPY, 5700.0),
            new FinancialTransaction(Currency.EUR, 6800.0));

    public static void main(String... args) {
        log.info("groupImperative");
        groupImperative();
        log.info("\ngroupFunctionally");
        groupFunctionally();
    }

    /**
     * Grouping a List of Transactions, java 7.
     * But since it is fun within the loop I am using a lambda for the initiation in case the currency is
     * not present as yet.
     *
     * References:
     * https://www.baeldung.com/java-map-computeifabsent
     * https://www.baeldung.com/java-enum-map
     */
    private static void groupImperative() {

        Map<Currency, List<FinancialTransaction>> transactionsByCurrency = new EnumMap<>(Currency.class);
        for (FinancialTransaction transaction : transactions) {
            Currency currency = transaction.getCurrency();
            transactionsByCurrency.computeIfAbsent(currency, k ->  new ArrayList<>()).add(transaction);
        }
        log.info("{}", transactionsByCurrency);
        // Java 7.
        log.info("Handling a Map<Currency, List<FinancialTransaction>> via a for-loop (Java 7).");
        for (Map.Entry<Currency, List<FinancialTransaction>> entry : transactionsByCurrency.entrySet()) {
            log.info("****************************************************");
            log.info("Currency: {}", entry.getKey());
            // Ooops: Java 8 :-) So in this way I can do everything with one of the values, being a List<String>.
            entry.getValue().stream()
                    .forEach(financialTransaction -> log.info("Name: {}", financialTransaction));
        }
    }

    private static void groupFunctionally() {
        Map<Currency, List<FinancialTransaction>> transactionsByCurrency = transactions
                .stream()
                .collect(Collectors.groupingBy(FinancialTransaction::getCurrency));
        transactionsByCurrency.forEach(
                (k,v) -> {
                    log.info("****************************************************");
                    log.info("Currency: {}", k);
                    v.stream()
                            .sorted(Comparator.comparing( transaction -> transaction.getValue() ))
                            .forEach( financialTransaction -> log.info("Transaction: {}", financialTransaction));
                }
        );
    }
}
