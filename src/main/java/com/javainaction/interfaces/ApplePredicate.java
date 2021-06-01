package com.javainaction.interfaces;

import com.javainaction.entities.Apple;

@FunctionalInterface
public interface ApplePredicate {

    public boolean test (Apple apple);
}
