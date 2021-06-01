package com.javainaction.chapter05;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.OptionalInt;
import java.util.stream.IntStream;

@Slf4j
public class Par06NumericStreams {

    public static void main(String... args) {

        log.info("Primitive stream specializations");

        log.info("Mapping to a numeric stream");
        OptionalInt optionalMaxCalories = Dish.menu.stream()
                .filter( (Dish dish) -> dish.getCalories() < 100)
                .mapToInt(Dish::getCalories)
                .max();
        // In ye olden days often -1 would be used to indicate that there is no content.
        log.info("Maximum number of calories of any of the dishes: {}", optionalMaxCalories.orElse(-1));

        log.info("Determine the number of even number in the range between 1 and 100, included.");
        long nrEvenNumbers = IntStream.rangeClosed(1, 100)
                .filter( (int i) -> i%2 == 0)
                .count();
        log.info("The number of even numbers is {}", nrEvenNumbers);
    }
}
