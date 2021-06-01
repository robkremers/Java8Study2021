package com.javainaction.chapter05;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Optional;

@Slf4j
public class Par03Finding {

    public static void main(String... args) {

        if (isVegetarianFriendlyMenu()) {
            log.info("Vegetarian friendly menus can be served");
        } else {
            log.info(" No vegetarian friendly menus can be served.");
        }

        log.info("Are all menus below 1000 calories: {}", isBelow1000Calories());
        log.info("Check that no menu is above 1000 calories: {}", checkNoneAbove1000Calories());

        Optional<Dish> optionalDish = Dish.menu.stream()
                .filter( Dish::isVegetarian)
                .findAny();

        if (optionalDish.isPresent()) {
            // Do something.
        }

        Dish.menu.stream()
                .filter( Dish::isVegetarian)
                .findAny()
                .ifPresent( dish -> log.info("A vegetarian dish can be served. e.g. {}", dish.getName()));

        Arrays.asList(1, 2, 3, 4, 5).stream()
                .map( i -> i * i)
                .filter( i -> i%3 == 0)
                .findAny()
                .ifPresent( i -> log.info("A number calculated with i^2, that can be divided by 3, has been found: {}", i));

    }

    public static boolean isVegetarianFriendlyMenu() {
        return Dish.menu.stream().anyMatch( (Dish dish) -> dish.isVegetarian());
    }

    public static boolean isBelow1000Calories() {
        return Dish.menu.stream().allMatch( dish -> dish.getCalories() < 1000);
    }

    public static boolean checkNoneAbove1000Calories() {
        return Dish.menu.stream().noneMatch( dish -> dish.getCalories() > 1000);
    }
}
