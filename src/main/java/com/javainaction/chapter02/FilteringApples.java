package com.javainaction.chapter02;

import com.javainaction.entities.Apple;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class FilteringApples {

    public static void main(String... args) {

        log.info("Chapter 2: Behavior parameterization");
        /**
         * Most of the code is available in chapter01.FilteringApples.
         * Especially look again at the use of lambda (Predicate) expresions.
         */
        List<Apple> inventory = Arrays.asList(new Apple(80, "green")
                , new Apple(155, "green")
                , new Apple(120, "red")
                , new Apple(100, "brown"));

        // It is possible to predefine an instance of type Consumer<T>.
        Consumer<Apple> consumer = (Apple apple) -> log.info(" {}", apple.getColor());

        log.info("Using a lambda defining a Consumer<T>");
        filterItems(inventory, ( Apple apple ) ->log.info("This apple is {}", apple) );

        log.info("Using a predefined instance of type Consumer<T>");
        filterItems(inventory, consumer);

        log.info("Using a predefined instance of type Consumer<T> to return multiple properties of the apples.");
        consumer = (Apple apple) -> {
            String characteristic = apple.getWeight() > 150 ? "heavy" : "light";
            log.info( "A {} {} apple", characteristic, apple.getColor());
        };
        filterItems(inventory, consumer);

    }

    /**
     * Method to print properties of instances of class T, depending on the requirements.
     * Okay, the statement in the method is not yet treated but the for-loop can be
     * replaced and the method is not even necessary anymore.
     * But I'll leave it in place for now.
     *
     * @param inventory
     * @param consumer
     * @param <T>
     */
    public static <T> void filterItems(List<T> inventory, Consumer<T> consumer) {
//        for (T t : inventory) {
//            consumer.accept(t);
//        }
        inventory.stream().forEach(consumer::accept);
    }
}
