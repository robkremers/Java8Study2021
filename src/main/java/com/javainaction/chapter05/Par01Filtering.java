package com.javainaction.chapter05;

import com.javainaction.entities.Dish;
import com.javainaction.enums.Type;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Par01Filtering {
    // §5.1. Par01Filtering and slicing.

    public static void main(String... args) {

        log.info("Par01Filtering with Predicate");
        List<Dish> vegetarianDishes =  Dish.menu
                .stream()
                .filter((Dish dish) -> dish.isVegetarian())
                .collect(toList());
        vegetarianDishes.forEach( (Dish dish) -> log.info("{}", dish.getName()));

        log.info("The same using method reference:");
        vegetarianDishes = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        vegetarianDishes.forEach( (Dish dish) -> log.info("{}", dish.getName()));

        log.info("Par01Filtering unique even elements in a stream, based on an array containing 1, 2, 1, 3, 3, 2, 4.");
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream()
                .filter( i -> i%2 == 0)
                .distinct()
                .forEach( i -> log.info("{}", i));

        log.info("Truncating a stream using 'limit()' while looking for dishes > 300 calories.");
        List<Dish> dishesLimit3 = Dish.menu.stream()
                .filter( dish -> dish.getCalories() > 300)
                .limit(3)
                .collect(toList());
        dishesLimit3.forEach( dish -> log.info("{}", dish.getName()));

        log.info("Skipping the first two elements of a stream");
        Dish.menu.stream()
                .filter( dish -> dish.getCalories() > 300)
                .skip(2)
                .forEach( dish -> log.info("{}", dish.getName()));

        log.info("Filter the first two meat dishes");
        Dish.menu.stream()
                .filter( dish -> dish.getType() == Type.MEAT)
                .limit(2)
                .forEach( dish -> log.info("{}", dish.getName()));

    }
}
