package com.javainaction.chapter04;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class StreamBasic {

    public static void main(String... args) {
        // Java 7
        log.info("Determine names of dishes with < 400 calories in descending order of the amount of calories using Java 7.");
        getLowCaloricDishNamesInJava7(Dish.menu).stream().forEach( (String dishName) -> log.info("{}", dishName));

        // Java 8
        log.info("Determine names of dishes with < 400 calories in descending order of the amount of calories using Java 8.");
        getLowCaloricDishNamesInJava8(Dish.menu).stream().forEach( System.out::println);

        log.info("Determine the first three names of dishes with > 300 calories using Java 8.");
        getFirstThreeHighCaloricDishNames(Dish.menu).stream().forEach( (String dishName) -> log.info("{}", dishName));
    }

    /**
     * Return the names of dishes with < 400 calories in descending order of the amount of calories.
     * This method shows the methodology of Java 7.
     * Therefore SonarLint will not like it. DO NOT CHANGE. It's an example.
     *
     * @param dishes
     * @return
     */
    public static List<String> getLowCaloricDishNamesInJava7(List<Dish> dishes) {
        List<Dish> lowCaloricDishes = new ArrayList<>();
        for( Dish dish: dishes) {
            if (dish.getCalories() < 400) {
                lowCaloricDishes.add(dish);
            }
        }
        Collections.sort(lowCaloricDishes, new Comparator<Dish>() {
            @Override
            public int compare(Dish dish1, Dish dish2) {
                return Integer.compare(dish1.getCalories(), dish2.getCalories());
            }
        });
        List<String> lowCaloricDishNames = new ArrayList<>();
        for (Dish dish: lowCaloricDishes) {
            lowCaloricDishNames.add(dish.getName());
        }
        return lowCaloricDishNames;
    }

    public static List<String> getLowCaloricDishNamesInJava8(List<Dish> dishes) {
        return dishes.stream()
                .filter( (Dish dish ) -> dish.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
    }

    /**
     * Select the first three names of dishes with more than 300 calories.
     *
     * @param dishes
     * @return
     */
    public static List<String> getFirstThreeHighCaloricDishNames(List<Dish> dishes) {
        return dishes.stream()
                .filter( (Dish dish) -> dish.getCalories() > 300)
                .map( (Dish dish) -> dish.getName() + " with " + dish.getCalories() + " calories" )
                .limit(3)
                .collect(Collectors.toList());
    }
}
