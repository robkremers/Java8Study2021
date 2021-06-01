package com.javainaction.entities;

import com.javainaction.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

// https://docs.oracle.com/javase/tutorial/collections/streams/index.html
@Slf4j
//@AllArgsConstructor
@Data
public class Person {

    private String name;
    private LocalDate birthday;
    private Sex gender;
    private String emailAddress;

    private int age;

    public Person( String name, LocalDate birthday, Sex gender, String emailAddress) {
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.emailAddress = emailAddress;
        this.age = LocalDate.now().getYear() - this.birthday.getYear();
    }

}
