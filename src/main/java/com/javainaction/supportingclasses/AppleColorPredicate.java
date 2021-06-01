package com.javainaction.supportingclasses;

import com.javainaction.entities.Apple;
import com.javainaction.interfaces.ApplePredicate;

public class AppleColorPredicate implements ApplePredicate {
    @Override
    public boolean test(Apple apple) {
        return apple.getColor().equals("green");
    }
}
