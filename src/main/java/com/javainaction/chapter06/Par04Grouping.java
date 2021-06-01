package com.javainaction.chapter06;

import com.javainaction.entities.Dish;
import com.javainaction.enums.CaloricLevel;
import com.javainaction.enums.Type;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class Par04Grouping {

    public static void main(String... args) {

        groupDishesByType();
        groupDishNamesByType();
        groupDishesByCaloricLevel();
        groupDishesByTypeAndCaloricLevel();
        countingDishTypes();
        groupDishesMostCaloricByType();
        getTotalCaloriesByType();
        getCaloricLevelsByType();
    }

    // Using: groupingBy(Function<? super T,? extends K> classifier)
    private static void groupDishesByType() {
        log.info("=============================================================");
        Map<Type, List<Dish>> dishesByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));

        dishesByType.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Type: {}", key);
            value.stream()
                    .forEach(dish -> log.info("  Name: {}", dish.getName()));
        });
    }

    // Using: groupingBy(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream)
    private static void groupDishNamesByType() {
        log.info("=============================================================");
        Map<Type, List<String>> dishesByName = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toList())));
        dishesByName.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Type: {}", key);
            value.stream()
                    .forEach(name -> log.info("  Name: {}", name));
        });
    }

    /**
     * Dish tags are defined in Class Dish.
     */
    private static void groupDishTagsByType() {

    }

    /**
     * The enum CaloricLevel is used for grouping.
     * Example: Listing 6.2. Multilevel grouping
     * Using: groupingBy(Function<? super T,? extends K> classifier, Collector<? super T,A,D> downstream)
     */
    private static void groupDishesByCaloricLevel() {
        log.info("=============================================================");
        Map<CaloricLevel, List<Dish>> dishesByCaloricLevel = Dish.menu.stream()
                .collect(Collectors.groupingBy(
                        (Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                ));
        dishesByCaloricLevel.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Caloric level: {}", key);
            value.stream()
                    .forEach(dish -> log.info("  Name: {}", dish.getName()));
        });
    }

    /**
     * Example: Listing 6.2. Multilevel grouping.
     * In this case by Type and by CaloricLevel.
     */
    private static void groupDishesByTypeAndCaloricLevel() {
        log.info("=============================================================");
        Map<Type, Map<CaloricLevel, List<Dish>>> groupDishesByTypeAndCaloricLevel = Dish.menu.stream()
                .collect(Collectors.groupingBy(
                        Dish::getType, Collectors.groupingBy((Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        })
                ));
        log.info("");
        log.info("Group Dishes by Type and Caloric Level");
        groupDishesByTypeAndCaloricLevel.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Dish type: {}", key);
            value.forEach((key2, value2) -> {
                log.info("  ++++++++++++++++++++++++++++++++++++++++++++++++++++");
                log.info("  Caloric level: {}", key2);
                value2.stream().forEach((Dish dish) -> log.info("  Name: {}", dish.getName()));
            });
        });
    }

    /**
     * The second collector passed to the first groupingBy can be any type of collector,
     * not just another groupingBy.
     * For instance, it’s possible to count the number of Dishes in the menu for each type, by passing the counting
     * collector as a second argument to the groupingBy collector.
     */
    private static void countingDishTypes() {
        log.info("=============================================================");
        Map<Type, Long> typesCount = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        log.info("");
        log.info("The numbers of dishes per type");
        typesCount.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Dish type: {}: nr. of dishes: {}", key, value);
        });
    }

    /**
     * Find the highest-calorie dish in the menu per dish type.
     * Note that if for a given type no dish is available the type will not be shown at all.
     */
    private static void groupDishesMostCaloricByType() {
        log.info("=============================================================");
        Map<Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream()
                .collect(Collectors.
                        groupingBy(Dish::getType,
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        log.info("");
        log.info("The dishes with the most calories per type");
        mostCaloricByType.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Dish type: {}", key);
            if (value.isPresent()) {

                log.info("  Dish with the most calories: {}", value.get().getName());
            } else {
                log.info("  For this type no dishes are available");
            }
        });
    }

    private static void getTotalCaloriesByType() {
        log.info("=============================================================");
        Map<Type, Integer> totalCaloriesByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));
        log.info("");
        log.info("The total number of calories per type");
        totalCaloriesByType.forEach((key, value) -> {
            log.info("****************************************************");
            log.info("Dish type {} with {} calories", key, value);

        });
    }

    private static void getCaloricLevelsByType() {
        log.info("=============================================================");
        Map<Type, Set<CaloricLevel>> caloricLevelsByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(
                        (Dish dish) -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }, Collectors.toCollection(HashSet::new)
                        )
                ));
        log.info("");
        log.info("The caloric levels per type");
        caloricLevelsByType.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Dish type: {}", key);
            value.stream().sorted().forEach( level -> log.info("Caloric level: {}", level));
        });
    }

}
