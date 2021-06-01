package com.javainaction.chapter05;

import com.javainaction.entities.Dish;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class Par02Mapping {

    public static void main(String... args) {

        log.info("Use Mapping to extract the names of the dishes in the stream");
        List<String> dishNames = Dish.menu.stream()
                .map( Dish::getName)
                .collect(Collectors.toList());
        dishNames.stream().forEach( name -> log.info("{}", name));

        log.info("Determine the length of the words in a String List");
        List<String> words = Arrays.asList("Java8", "Lambdas", "In", "Action");
        words.stream()
                .forEach( word -> log.info("{} has length {}", word, word.length()));

        log.info("Determine the length of the words in a String List using Mapping");
        List<Integer> numbers = words.stream()
                .map( word -> word.length())
                .collect(Collectors.toList());
        numbers.stream().forEach( number -> log.info("{}", number));

        log.info("Determine the length of the names of each dish");
        List<Integer> dishNameLength = Dish.menu.stream()
                .map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        dishNameLength.stream().forEach( string -> log.info("{}", string));

        log.info("Using flatMap to map the results of multiple Lists / Streams into a single stream");
        // map( word -> word.split("")) --> Converts each word into an array of it's individual letters.
        // flatMap( Arrays::stream)     --> Flattens each generated stream into a single stream.
        // Using the flatMap method has the effect of mapping each array not with a stream but with the
        // contents of that stream.
        words.stream()
                .map( word -> word.split(""))
                .flatMap( Arrays::stream)
                .distinct()
                .forEach( letter -> log.info(letter));

        // Given a list of numbers, how would you return a list of the square of each number?
        log.info("[1, 2, 3, 4, 5] --> [1, 4, 9, 16, 25]");
        List<Integer> numbers1 = Arrays.asList(1,2,3,4,5);
        List<Integer> numbers2 = Arrays.asList(6,7,8);
        numbers1.stream()
                .map( i -> i * i)
                .forEach( i -> log.info("{}", i));

        // Given two lists of numbers, how would you return a list of the square of each number?
        log.info(" Two lists: [1, 2, 3, 4, 5], [6, 7, 8]");
        List<int[]> pairs = numbers1.stream()
                .flatMap((Integer i) -> numbers2.stream()
                        .map((Integer j) -> new int[]{i, j})
                )
                .filter(pair -> (pair[0] + pair[1]) % 3 == 0)
                .collect(Collectors.toList());
        pairs.stream().forEach( (int[] pair) -> log.info("{},{}", pair[0], pair[1]));

    }
}
