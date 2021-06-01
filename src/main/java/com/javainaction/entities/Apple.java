package com.javainaction.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Apple {

    private Integer weight = 0;
    private String color = "";

    public static boolean isGreenApple(Apple apple) {
        return "green".equals(apple.getColor());
    }

    public static boolean isHeavyApple(Apple apple) {
        return apple.getWeight() > 150;
    }

    public static boolean isHeavyApple(Apple apple, int weight) {
        return apple.getWeight() > weight;
    }
}
