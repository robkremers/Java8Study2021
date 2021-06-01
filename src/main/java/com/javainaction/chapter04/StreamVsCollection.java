package com.javainaction.chapter04;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class StreamVsCollection {

    public static void main(String... args) {

        List<String> title = Arrays.asList("Java8", "In", "Action");
        Stream<String> stream = title.stream();
        stream.forEach( (String word) -> log.info("{}", word));
        // Sonarlint: Consumed Stream pipelines should not be reused.
        // java.lang.IllegalStateException will be thrown at runtime.
//        stream.forEach( (String word) -> log.info("{}", word));
    }
}
