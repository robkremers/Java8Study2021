package com.javainaction.chapter05;

import com.javainaction.chapter03.ExecuteAround;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Par07BuildingStreams {

    private static String dataFile = "javainaction/chapter05/data.txt";

    public static void main(String... args) throws IOException {

        Stream<String> stream = Stream.of( "Java 8", "Lambdas", "In", "Action");
        stream.forEach( word -> log.info("{}", word));

        Stream<String> emptyStream = Stream.empty();
        log.info("Empty stream: {}", emptyStream.collect(Collectors.toList()));

        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();
        log.info("The sum of {} is {}", numbers, sum);

        // This example will normally not work because Paths.get() requires an absolute path.
        // http://tutorials.jenkov.com/java-nio/path.html
//        long uniqueWords = Files.lines(Paths.get("data.txt"), Charset.defaultCharset())
//                .flatMap(line -> Arrays.stream(line.split(" ")))
//                .distinct()
//                .count();

        List<String> lines = processFile(dataFile);
        long uniqueWords = lines.stream()
                .flatMap( (String line) -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();
        System.out.println("There are " + uniqueWords + " unique words in data.txt");

        // Stream.iterate(T seed, UnaryOperator<T> f)
        // Interface UnaryOperator<T> with T - the type of the operand and result of the operator
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach( number -> log.info("{}",number));
    }

    // The old-fashioned way. But it works.
    // That is: find a file in the project based on a relative path.
    public static List<String> processFile(String dataFile) throws IOException {
        InputStream inputStream = ExecuteAround.class.getClassLoader().getResourceAsStream(dataFile);
        List<String> lines = new ArrayList<>();
        try( BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream))) {
            lines.addAll(bufferedReader.lines().collect(Collectors.toList()));
        }
        return lines;
    }
}
