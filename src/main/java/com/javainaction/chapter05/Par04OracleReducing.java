package com.javainaction.chapter05;

import com.javainaction.entities.Person;
import com.javainaction.enums.Sex;
import com.javainaction.utilities.Averager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Some additional info regarding streams / reduction from Oracle:
 * - https://docs.oracle.com/javase/tutorial/collections/streams/index.html
 * - https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html
 */
@Slf4j
public class Par04OracleReducing {

    public static void main(String... args) {

        List<Person> roster = Arrays.asList(
                new Person("Rob", LocalDate.of(1962, 03, 07), Sex.MALE, "robkremers@hotmail.com")
                , new Person("Arjen", LocalDate.of(1965, 01, 01), Sex.MALE, "avdweijden@bkwi.nl")
                , new Person("Anja", LocalDate.of(1960, 5, 26), Sex.FEMALE, "slikkerveer@live.nl")
        );

        // jdk 7
        for (Person person : roster) {
            log.info("Name = {}", person.getName());
        }

        // jdk 8
        roster.stream().forEach( (Person person) -> log.info("Name = {}", person.getName()));

        // The name of all male persons in a roster.
        roster.stream()
                .filter( person -> person.getGender().equals(Sex.MALE))
                .forEach(person -> log.info("Name of male person = {}", person.getName()));

        // Calculation of the average age of all male members contained in the collection roster
        double averageAge = roster
                .stream()
                .filter( person -> person.getGender().equals(Sex.MALE))
                .mapToInt(Person::getAge)
                .average()
                .getAsDouble();
        log.info("The average age of the male persons on the roster is {}", averageAge);

        // 2021-05-07:
        // https://docs.oracle.com/javase/tutorial/collections/streams/reduction.html

        Integer totalAge = roster
                .stream()
                .mapToInt(Person::getAge)
                .sum();
        log.info("Total age sum of all people on the roster is {}", totalAge);

        // Using Stream.reduce
        totalAge = roster
                .stream()
                .map(Person::getAge)
                .reduce( 0, (a , b) -> a + b);
        log.info("Total age sum of all people on the roster is {}", totalAge);

        // https://docs.oracle.com/javase/8/docs/api/?java/util/stream/Stream.html
        // Using Stream.collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
        // Performs a mutable reduction operation on the elements of this stream.
        // Unlike the reduce method, which always creates a new value when it processes an element,
        // the collect method modifies, or mutates, an existing value.
        // For this purpose you can create a new data type that contains member variables, keeping track of the values.
        Averager averageCollect = roster.stream()
                .filter( person -> person.getGender().equals(Sex.MALE))
                .map(Person::getAge)
//                .collect(Averager::new, Averager::accept, Averager::combine)
                .collect( () -> {return new Averager();}, Averager::accept, Averager::combine)
                ;
        log.info("Average age of male members: {}", averageCollect.getAverage());
        log.info("count: {}", averageCollect.getCount());
        log.info("sum  : {}", averageCollect.getTotal());

        // The groupingBy operation returns a map whose keys are the values that result from applying
        // the lambda expression specified as its parameter (which is called a classification function).
        List<String> namesOfMaleMembers = roster
                .stream()
                .filter( person -> person.getGender().equals(Sex.MALE))
                .map( Person::getName)
                .collect(Collectors.toList());

        namesOfMaleMembers.stream().forEach( name -> log.info("Name: {}", name));

        // Grouping members of the roster by gender.
        Map<Sex, List<Person>> membersByGender = roster
                .stream()
                .collect(Collectors.groupingBy(Person::getGender));

        membersByGender.get(Sex.FEMALE)
                .stream().forEach( person -> log.info("Female: {}", person.getName()));
        membersByGender.get(Sex.MALE)
                .stream().forEach( person -> log.info("Male: {}", person.getName()));

        // Retrieve the names of each member in the collection roster and groups them by gender:
//        select count(*)
//        ,      r.gender
//        ,      r.name
//        from   roster r
//        group by r.gender
//        ,      r.name
//        order by r.gender
//        ,      r.name
        // java.util.stream.Collectors.mapping(Function<? super T,? extends U> mapper, Collector<? super U,A,R> downstream)
        Map<Sex, List<String>> namesByGender =
                roster
                .stream()
                .collect(Collectors.groupingBy(
                        Person::getGender,
                        Collectors.mapping(
                                Person::getName,
                                Collectors.toList()
                        )
                ));

        // Java 7.
        log.info("Handling a Map<Sex, List<String>> via a for-loop (Java 7).");
        for (Map.Entry<Sex, List<String>> entry : namesByGender.entrySet()) {
            log.info("****************************************************");
            log.info("Gender: {}", entry.getKey());
            // Ooops: Java 8 :-) So in this way I can do everything with one of the values, being a List<String>.
            entry.getValue().stream()
                    .forEach( name -> log.info("Name: {}", name));
        }

        // java 8
        log.info("Handling a Map<Sex, List<String>> via forEach and a lambda (Java 8).");
        namesByGender.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Gender: {}", key);
            value.stream()
                    .forEach( name -> log.info("Name: {}", name));
        });

        // Retrieve the total age of members of each gender:
        Map<Sex, Integer> totalAgeByGender =
                roster
                .stream()
                .collect(Collectors.groupingBy(
                   Person::getGender,
                   Collectors.reducing(
                           0,
                           Person::getAge,
                           Integer::sum
                   )
                ));

        log.info("Show the total age by gender of the persons on the roster.");
        totalAgeByGender.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Gender: {}", key);
            log.info("Total age: {}", value);
        });

        // Retrieve the average age of members of each gender:
        Map<Sex, Double> averageAgeByGender =
                roster
                .stream()
                .collect(Collectors.groupingBy(
                        Person::getGender,
                        Collectors.averagingDouble(Person::getAge)
                ));
        log.info("Show the average age by gender of the persons on the roster.");
        averageAgeByGender.forEach( (key, value) -> {
            log.info("****************************************************");
            log.info("Gender: {}", key);
            log.info("Average age: {}", value);
        });

    }
}
