package com.javainaction.chapter03;

import com.javainaction.entities.Apple;
import com.javainaction.interfaces.NoInputAction;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import static java.util.Comparator.comparing;

@Slf4j
public class Sorting {

    public static void main(String... args) {
        log.info("Chapter 3: Lambda expressions; sorting");

        /**
         * Most of the code is available in chapter01.FilteringApples.
         * Especially look again at the use of lambda (Predicate) expresions.
         */
        List<Apple> inventory = Arrays.asList(new Apple(155, "orange")
                , new Apple(80, "green")
                , new Apple(155, "green")
                , new Apple(120, "red")
                , new Apple(100, "brown")
                );

        List<String> characterStrings = Arrays.asList("a", "b", "A", "B");

        // A Lambda using an implicit java.util.function.BiFunction setup.
        Comparator<Apple> comparingByWeight = (Apple apple1, Apple apple2) -> apple1.getWeight().compareTo(apple2.getWeight());
        // can be rewritten, using method reference:
        comparingByWeight = comparing(Apple::getWeight);

        // A Runnable instance is an implicit implementation with the format of a functional interface.
        // A Runnable instance accepts: () -> {} (signature () -> void).
        Runnable runnable = () -> log.info("An instance of Runnable is a functional interface of type Consumer.");
        runnable.run();

        // Older version: using an anonymous class.
        runnable = new Runnable() {
            @Override
            public void run() {
                log.info("Again a runnable but now using an anonymous class");
            }
        };
        runnable.run();

        /**
         * A Lambda expression lets you provide the implementation of the abstract method of a
         * functional interface directly inline and treat the whole expression as an instance
         * of a functional interface.
         */
        process(() -> log.info("Processing the Consumer Runnable via a method header parameter."));

        NoInputAction noInputAction = () -> {
            log.info("Execution of an action without input.");
        };
        process2(noInputAction);

        /**
         * Sorting (page. 83) as an example of method referencing.
         */
        // First copy
        List<String> sortedCharacterStrings = new ArrayList<String>(characterStrings);
        sortedCharacterStrings.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
        log.info("Overview of the original characterList: {}", characterStrings);
        log.info("Overview of the sorted characterList: {}", sortedCharacterStrings);

        /**
         * Example of method reference to a static method.
         */
        Function<String, Integer> stringToInteger = (String s) -> Integer.parseInt(s);
        // can be rewritten, using method reference:
        stringToInteger = Integer::parseInt;

        /**
         * Example of method reference to an instance method of an arbitrary method.
         * The following is possible because:
         * boolean 	List.contains(Object o)
         * and
         * the target type describes a function descriptor (List<String>, String) -> boolean.
         */
        BiPredicate<List<String>, String> contains = (list, element) -> list.contains(element);
        // can be rewritten, using method reference:
        contains = List::contains;

        /**
         * § 3.7. Putting lambdas and method references into practice!
         */
        log.info("Overview of the apple inventory:");
        log.info("{}", inventory);
        log.info("Overview of the apple inventory, sorted ascending by weight:");
        List<Apple> inventorySortedByWeight = new ArrayList<>(inventory);
        inventorySortedByWeight.sort(comparingByWeight);
        log.info("{}", inventorySortedByWeight);
        log.info("Overview of the apple inventory, sorted descending by weight:");
        // Now using the static method Comparator.comparing in combination with the reversed sorting.
        inventorySortedByWeight.sort(comparing(Apple::getWeight).reversed());
        log.info("{}", inventorySortedByWeight);
        // Chaining comparators.
        log.info("Sorting by weight and then by color using Comparator chaining.");
        inventorySortedByWeight.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
        log.info("{}", inventorySortedByWeight);

        /**
         * ± 3.8.3. Composing Functions
         */
        Function<Integer, Integer> f = x -> x + 1;
        UnaryOperator<Integer> g = x -> x * 2;
        // h.andThen: First f, then g.
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(2);
        log.info("Result after AndThen = {}", result);
        // h.compose: First g, then f.
        Function<Integer, Integer> j = f.compose(g);
        result = j.apply(2);
        log.info("Result after compose = {}", result);


    }

    public static void process(Runnable runnable) {
        runnable.run();
    }

    public static void process2(NoInputAction noInputAction) {
        noInputAction.run();
    }
}
