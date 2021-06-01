package com.javainaction.chapter05;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;

@Slf4j
public class Par04Reducing {

    public static void main(String... args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        /**
         * In reduce is used:
         * - T identity: used as an initial value.
         * - A BinaryOperator<T>: used to combine two elements and produce a new value.
         */
        int sum = numbers.stream()
                .reduce( 0, (a, b) -> a + b);
        log.info("sum = {}", sum);
        sum = numbers.stream()
                .reduce(0, Integer::sum);
        log.info("sum = {}", sum);

        // Defining a BinaryOperator separately:
        BinaryOperator<Integer> summation = (Integer a, Integer b) -> a + b;
        BinaryOperator<Integer> multiply = (a, b) -> a * b;
        // So it can also be written as:
        sum = numbers.stream().reduce(0, summation);
        log.info("sum = {}", sum);
        int multiplication = numbers.stream().reduce(1, multiply);
        log.info("multiplication = {}", multiplication);

        // Method reference can also be used:
        // The first element of List numbers is used as initial value.
        int minimum = numbers.stream().reduce(numbers.get(0), Integer::min);
        log.info("minimum = {}", minimum);
        log.info("maximum = {}", numbers.stream().reduce(numbers.get(0), Integer::max));

        /**
         * reduce(BinaryOperator<T> accumulator) returns Optional<T>.
         * This overloaded version of Stream.reduce() is used if it is possible that
         * that the stream does not contain any value.
         */
        List<Integer> noNumbers = Arrays.asList();

        Optional<Integer> noNumber = noNumbers.stream().reduce(Integer::sum);
        log.info("noNumber = {}", noNumber.orElse(0));

        Optional<Integer> maxNumber = numbers.stream()
                .reduce(Integer::max);
        log.info("Maximum value of numbers is {}", maxNumber.orElse(0));

        // Quiz 5.3: Reducing: How would you count the number of dishes in a stream using the map and reduce methods?
        Optional<Integer> numberOfDishes = Dish.menu.stream()
                .map( dish -> 1)
                .reduce(Integer::sum)
                ;
        log.info("Number of dishes: {}", numberOfDishes.orElse(0));
        // or:
        int intNumberOfDishes = Dish.emptyMenu.stream()
                .map( dish -> 1)
                .reduce(0, (a, b) -> a + b)
                ;
        log.info("Number of dishes: {}", intNumberOfDishes);

        // Alternative (actually this would normally be used):
        long longNumberOfDishes = Dish.menu.stream().count();
        log.info("Number of dishes: {}", longNumberOfDishes);

    }
}
