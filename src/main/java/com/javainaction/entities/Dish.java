package com.javainaction.entities;

import com.javainaction.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

import static java.util.Arrays.asList;

@Data
@AllArgsConstructor
public class Dish {

    private static final long serialVersionUID = -2348532826532126049L;

    private final String name;
    private final boolean vegetarian;
    private final int calories;
    private final Type type;

    public static final List<Dish> menu =
            Arrays.asList( new Dish("pork", false, 800, Type.MEAT),
                    new Dish("beef", false, 700, Type.MEAT),
                    new Dish("chicken", false, 400, Type.MEAT),
                    new Dish("french fries", true, 530, Type.OTHER),
                    new Dish("rice", true, 350, Type.OTHER),
                    new Dish("season fruit", true, 120, Type.OTHER),
                    new Dish("pizza", true, 550, Type.OTHER),
                    new Dish("prawns", false, 400, Type.FISH),
                    new Dish("salmon", false, 450, Type.FISH));

    public static final List<Dish> emptyMenu = new ArrayList<>();

    public static final Map<String, List<String>> dishTags = new HashMap<>();

    static {
        dishTags.put("pork", asList("greasy", "salty"));
        dishTags.put("beef", asList("salty", "roasted"));
        dishTags.put("chicken", asList("fried", "crisp"));
        dishTags.put("french fries", asList("greasy", "fried"));
        dishTags.put("rice", asList("light", "natural"));
        dishTags.put("season fruit", asList("fresh", "natural"));
        dishTags.put("pizza", asList("tasty", "salty"));
        dishTags.put("prawns", asList("tasty", "roasted"));
        dishTags.put("salmon", asList("delicious", "fresh"));
    }

}
