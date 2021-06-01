package com.javainaction.chapter03;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

@Slf4j
public class Lambdas {

    public static void main(String... args) {

        List<String> strings = Arrays.asList("1", "2", "a", "b", "");
        List<String> words = Arrays.asList("Lambdas", "in", "Action");

        // Working with Interface Predicate<T>.
        Predicate<String> nonEmptyStringPredicate = (String string) -> !string.isEmpty();

        List<String> nonEmptyStrings = filter(strings, nonEmptyStringPredicate);
        log.info("Overview of non-empty strings: ");
        log.info("{}", nonEmptyStrings);

        // Working with Consumer<T>.
        log.info("Overview of all strings in List 'strings':");
        printList(strings, (String string) -> log.info("{}", string) );

        // Working with Function<T,R>.
        // In this case it is a Lambda of the form Function<String, Integer>: (String string) -> string.length()
        List<Integer> lengthList = words
                .stream()
                .map((String string) -> string.length())
                .collect(Collectors.toList());
        log.info("Overview of the length of the strings in List 'words':");
        log.info("{}", lengthList);

        // Again working with Function<T, R>: Function<String, Integer>.
        // Now going even further Method Reference is used.
        // In order to sum the resulting String lengths mapToInt is used.
        int totalLength = words
                .stream()
                .map(String::length)
                .mapToInt(Integer::intValue)
                .sum();
        log.info("The total length of the words in List 'words' is {}", totalLength);

        // Specialization for the use of primitives.
        // This avoids Boxing around primitives, which will lower the performance.
        IntPredicate evenNumbers = (int i) -> i%2 == 0;
        IntPredicate oddNumbers = (int i) -> i%2  == 1;
        log.info("Is 1000 an even number? {}", evenNumbers.test(1000));
        log.info("Is 99 an even number? {}", evenNumbers.test(99));
        log.info("Is 105 an odd number? {}", oddNumbers.test(105));

        log.info("Is 109 > 100 and an odd number? {}", oddNumbers.and(i -> i > 100).test(109));
        log.info("Is 19 > 100 and an odd number? {}", oddNumbers.and(i -> i > 100).test(19));

        // Example of ObjDoubleConsumer<T>: ObjDoubleConsumer<String>.
        // Note that the second parameter should be a 'double', not a 'Double'.
        // Prevents (again) the necessity of boxing.
        final ObjDoubleConsumer<String> objDoubleConsumer = (String string, double d)-> log.info("Test for ObjDoubleConsumer<T> {}", string.length() + d);
        objDoubleConsumer.accept("test", 4.5);
        objDoubleConsumer.accept("test2", 5.5);

        // Example of IntBinaryOperator
        // Because the compiler can deduce the type of the parameters they can be left away.
        // Whether the type can be left away depends on the readability of the statement.
        IntBinaryOperator intBinaryOperator = (a, b) -> a *b;
        log.info("4 * 5 = {}", intBinaryOperator.applyAsInt(4, 5));

        /**
         * When using a local variable in a Lambda the local variable should be declared final
         * or is effectively final.
         * But in my view this is anyway bad coding.
         * Use instead of () -> {} a functional interface with a corresponding input parameter.
          */
        final int portNumber = 1337;
        Runnable runnable = () -> log.info("Portnumber = {}.", portNumber);
        runnable.run();
        // Taking the following out of comment would cause an error.
//        portNumber = 13337;

        /**
         * Instead use in the above mentioned example a functional interface of type Consumer,
         * which accepts an input parameter.
         * (or use a derivative, such as below IntConsumer).
         */
        int goodPortNumber = 1337;
        IntConsumer intConsumer = (int varPortNumber) -> log.info("Portnumber = {}.", varPortNumber);
        intConsumer.accept(goodPortNumber);
        goodPortNumber = 13337;
        intConsumer.accept(goodPortNumber);
    }

    public static <T> List<T> filter(List<T> list, Predicate<T> predicate) {
        return list.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <T> void printList(List<T> list, Consumer<T> consumer) {
        list.stream().forEach(consumer);
    }

}
