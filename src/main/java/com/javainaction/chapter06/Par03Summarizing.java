package com.javainaction.chapter06;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Par03Summarizing {

    public static void main(String... args) {
        calculateMostCalorieDish();
        calculateTotalNumberOfCaloriesInMenu();
        calculateAverageNrCalories();
        calculateMenuStatistics();
        getShortMenu();
        getShortMenuCommaSeparated();
        findMostCaloricDish();
        calculateNumberStream();
        quizJoiningStringsWithReducing();
    }

    private static void calculateMostCalorieDish() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt( Dish::getCalories);

        // Using a filled menu.
        Optional<Dish> mostCalorieDish =  Dish.menu.stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));
        if (mostCalorieDish.isPresent()) {
            log.info("Dish with the highest amount of calories: {}", mostCalorieDish.get());
        } else {
            log.info("No dish available");
        }

        // Using an empty menu.
        mostCalorieDish =  Dish.emptyMenu.stream()
                .collect(Collectors.maxBy(dishCaloriesComparator));
        if (mostCalorieDish.isPresent()) {
            log.info("Dish with the highest amount of calories: {}", mostCalorieDish.get());
        } else {
            log.info("No dish available");
        }
    }

    // In case the List is empty Collectors.summingInt will return a '0'.
    // Book:
    // While traversing the stream
    // each dish is mapped into its number of calories, and this number is added to an accumulator
    // starting from an initial value (in this case the value is 0).
    private static void calculateTotalNumberOfCaloriesInMenu() {
        int total = Dish.menu.stream()
                .collect(Collectors.summingInt( Dish::getCalories))
                ;
        log.info("Total number of calories of a filled menu card: {}", total);

        total = Dish.emptyMenu.stream()
                .collect(Collectors.summingInt( Dish::getCalories))
                ;
        log.info("Total number of calories of an empty menu card: {}", total);

        // Using Collectors.reducing.
        // reducing(U identity, Function<? super T,? extends U> mapper, BinaryOperator<U> op)
        total = Dish.menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        log.info("Total numbers of calories of a filled menu card using Collectors.reducing: {}", total);

        // reducing(T identity, BinaryOperator<T> op)
        // So the following does not work, because instead of a Dish an integer is returned.
        // You need to use a mapper as shown above because the input is a dish and the required value is an integer.
//        Dish.menu.stream()
//                .collect(Collectors.reducing( 0, (Dish d1, Dish d2) -> d1.getCalories() + d1.getCalories()));

        // Using Stream.map + Stream.reducing.
        total = Dish.menu.stream()
                .map(Dish::getCalories)
                .reduce(0, (i, j) -> i + j);
        log.info("Total numbers of calories of a filled menu card using Streams.map + Streams.reducing: {}", total);
    }

    // An example of using stream().reduce().
    // Use: reduce(U identity, BiFunction<U,? super T,U> accumulator, BinaryOperator<U> combiner)

    /**
     * This solution has two problems: a semantic one and a practical one.
     * The semantic problem lies in the fact that the reduce method is meant to combine two values and produce a new one;
     * it’s an immutable reduction.
     * In contrast, the collect method is designed to mutate a container to accumulate the result it’s supposed to
     * produce. This means that the snippet of code below is misusing the reduce method because it’s mutating in
     * place the List used as accumulator.
     * As you’ll see in more detail in the next chapter, using the reduce method with the wrong semantic is
     * also the cause of a practical problem: this reduction process can’t work in parallel because the
     * concurrent modification of the same data structure operated by multiple threads can corrupt the
     * List itself.
     * In this case, if you want thread safety, you’ll need to allocate a new List every time,
     * which would impair performance by object allocation.
     * This is the main reason why the collect method is useful for expressing reduction working on a mutable
     * container but crucially in a parallel-friendly way, as you’ll learn later in the chapter.
     */
    private static void calculateNumberStream() {
        List<Integer> numbers =  Arrays.asList(1, 2, 3, 4, 5, 6).stream()
                .reduce( new ArrayList<Integer>()
                        , (List<Integer> l, Integer i) -> {l.add(i); return l;}
                        , (List<Integer> l1, List<Integer> l2) -> { l1.addAll(l2); return l1;}
                        );
        log.info("numbers: {}", numbers);

        // Alternatives using collect.
        Arrays.asList(1,2, 3, 4, 5, 6).stream()
                .collect(Collectors.reducing(0, Integer::sum));
        // or
        Arrays.asList(1,2, 3, 4, 5, 6).stream().collect(Collectors.toList());
        // or
        Arrays.asList(1,2, 3, 4, 5, 6).stream().mapToInt( i -> i);

        // Of course the general approach would be:
        new ArrayList<>( Arrays.asList(1, 2, 3, 4, 5, 6));

    }

    private static void calculateAverageNrCalories() {
        double averageNrCalories = Dish.menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));
        log.info("The average number of calories over all menus is: {}", averageNrCalories);
    }

    // Using Collectors.summarizingInt to retrieve multiple summarizing results.
    // The implementation of Collectors.summarizingInt is not threadsave.
    private static void calculateMenuStatistics() {
        IntSummaryStatistics menuStatistics = Dish.menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));
        log.info("Menu statistics: {}", menuStatistics);
    }

    private static void getShortMenu() {
        String shortMenu = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());
        log.info("Short menu: {}", shortMenu);
    }

    private static void getShortMenuCommaSeparated() {
        String shortMenu = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(", "));
        log.info("Short menu: {}", shortMenu);
    }

    private static void findMostCaloricDish() {
        Optional<Dish> mostCaloricDish = Dish.menu.stream()
                .collect(Collectors.reducing((Dish dish1, Dish dish2) -> dish1.getCalories() > dish2.getCalories() ? dish1 : dish2));
        if (mostCaloricDish.isPresent()) {
            Dish dish = mostCaloricDish.get();
            log.info("The dish with the most calories is {} with {} calories", dish.getName(), dish.getCalories());
        } else {
            log.info("The menu is empty.");
        }
        Dish emptyDish = mostCaloricDish.orElseGet(null);

        mostCaloricDish = Dish.emptyMenu.stream()
                .collect(Collectors.reducing((Dish dish1, Dish dish2) -> dish1.getCalories() > dish2.getCalories() ? dish1 : dish2));
        if (mostCaloricDish.isPresent()) {
            Dish dish = mostCaloricDish.get();
            log.info("The dish with the most calories is {} with {} calories", dish.getName(), dish.getCalories());
        } else {
            log.info("The menu is empty.");
        }
    }

    private static void quizJoiningStringsWithReducing() {
        String shortMenu = Dish.menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());
        log.info("Short menu: {}", shortMenu);

        // What delivers the same result:
        // Solution 1: slightly adapted using String::concat.
        shortMenu = Dish.menu.stream()
                .map(Dish::getName)
                .collect( Collectors.reducing( String::concat)).get();
        log.info("Short menu: {}", shortMenu);

        // Solution 2: again using String::concat.
        shortMenu = Dish.menu.stream()
                .collect(Collectors.reducing("", Dish::getName, String::concat));
        log.info("Short menu: {}", shortMenu);
    }

}
