package com.javainaction.chapter06;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class Par05Partitioning {

    public static void main(String... args) {
        partitionByVegetarianMenu();
        usingPartitioningBy();
        investigeRangeForPrimes();

    }

    private static void partitionByVegetarianMenu() {
        Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian));
        partitionedMenu.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Is vegetarian: {}", key);
            value.stream().sorted(Comparator.comparing(Dish::getName)).forEach( (Dish dish) -> log.info("Dish: {}", dish.getName()));

        });
        List<Dish> vegetarianDishes = partitionedMenu.get(true);
    }

    private static void usingPartitioningBy() {
        // Using partitioningBy(Predicate<? super T> predicate, Collector<? super T,A,D> downstream)
        log.info("=============================================================");
        Map<Boolean, Map<Boolean, List<Dish>>> partitionedVegiFatMenu = Dish.menu.stream().collect( Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.partitioningBy( dish -> dish.getCalories() > 500)));
        partitionedVegiFatMenu.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Is Vegetarian: {}", key);
            value.forEach( (key2, value2) -> {
                log.info("{}Is Fat: {}", " ", key2);
                value2.forEach( dish -> log.info("{}Dish: {}", "   ", dish.getName()));
            });
        });

        // Using partitioningBy(Predicate<? super T> predicate, Collector<? super T,A,D> downstream)
        log.info("=============================================================");
        Map<Boolean, Long> partitionedMenu = Dish.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.counting()));
        partitionedMenu.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Is Vegetarian: {} with number of dishes {}", key, value);
        });
    }

    private static void investigeRangeForPrimes(){
        Map<Boolean, List<Integer>> rangeForPrimes = determinePrimes(25);
        log.info("=============================================================");
        rangeForPrimes.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Is prime: {}", key);
            value.forEach( i -> log.info("{}Number: {}", " ", i));
        });
    }

    public static Map<Boolean, List<Integer>> determinePrimes(int candidate) {
        return IntStream.rangeClosed(2, candidate)
                .boxed()
                .collect( Collectors.partitioningBy( n -> isPrime(n)));
    }

    /**
     * Determine if a number is a prime or not.
     * Test only for factors less than or equal to the square root of the candidate.
     * @param candidate An integer number.
     * @return boolean: true is the candidate is not divisible by any number between 2 and the candidate number.
     */
    public static boolean isPrime( int candidate) {
        int candidateRoot = (int) Math.sqrt( (double) candidate);
        return IntStream.range(2, candidateRoot)
                .noneMatch( (int i) -> candidate % i == 0);
    }
}
