package com.javainaction.chapter01;

import com.javainaction.entities.Apple;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class FilteringApples {

    public static void main(String... args) {

        List<Apple> inventory = Arrays.asList(new Apple(80, "green")
                , new Apple(155, "green")
                , new Apple(120, "red")
                , new Apple(100, "brown"));

        log.info("A List with three apples has been created: " + inventory.size());

        log.info("\nUsing java 7 methods");
        List<Apple> greenApples = filterGreenApples(inventory, "green");
        log.info("Overview of green apples: \n" + greenApples);

        List<Apple> heavyApples = filterHeavyApples(inventory, 150);
        log.info("Overview of heavy apples \n|" + heavyApples);

        log.info("\nUsing java 8 methods with the predicates defined in the class using them.");
        greenApples = filterItems(inventory, FilteringApples::isGreenApple);
        log.info("Overview of green apples: \n" + greenApples);
        heavyApples = filterItems(inventory, FilteringApples::isHeavyApple);
        log.info("Overview of heavy apples \n" + heavyApples);

        log.info("\nUsing java 8 methods with the predicates in class Apple");
        greenApples = filterItems(inventory, Apple::isGreenApple);
        log.info("Overview of green apples: \n" + greenApples);
        heavyApples = filterItems(inventory, Apple::isHeavyApple);
        log.info("Overview of heavy apples \n" + heavyApples);

        log.info("\nUsing java 8 methods with flexible predicates");
        greenApples = filterItems(inventory, (Apple apple) -> apple.getColor().equals("green"));
        log.info("Overview of green apples: \n" + greenApples);
        heavyApples = filterItems(inventory, (Apple apple) -> apple.getWeight() > 150);
        log.info("Overview of heavy apples \n" + heavyApples);

        // And now multiple predicates in one.
        // The use of curly braces '{}' is necessary if the functionality contains multiple statements.
        // That is not the case below. It's just an example what is possible.
        List<Apple> weirdApples = filterItems(inventory, (Apple apple) -> {
            return apple.getWeight() < 80 || apple.getColor().equals("brown");
        });
        log.info("Overview of weird apples \n" + weirdApples);

        log.info("\nUsing java 8 methods with Streams");
        greenApples = inventory.stream()
                .filter( (Apple apple) -> apple.getColor().equals("green"))
                .collect(Collectors.toList());
        log.info("Overview of green apples: \n" + greenApples);
        // An example of the use of parallelStream() which will utilize the multiple cores of the multiple cores processor.
        heavyApples = inventory.parallelStream()
                .filter( (Apple apple) -> apple.getWeight() > 150)
                .collect(Collectors.toList());
        log.info("Overview of heavy apples \n" + heavyApples);
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory, String color) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (color.equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory, int weight) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple : inventory) {
            if (apple.getWeight() > weight) {
                result.add(apple);
            }
        }
        return result;
    }

    // Java 8. Implicitly this is the implementation of a functional interface, a Predicate<T>.
    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

//    public static List<Apple> filterApples(List<Apple> inventory, Predicate<Apple> predicate) {
//        List<Apple> result = new ArrayList<>();
//        for (Apple apple : inventory) {
//            if (predicate.test(apple)) {
//                result.add(apple);
//            }
//        }
//        return result;
//    }

    // The generic version.
    public static <T> List<T> filterItems(List<T> inventory, Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        for (T t : inventory) {
            if (predicate.test(t)) {
                result.add(t);
            }
        }
        return result;
    }
}
