package com.javainaction.javacodegeeks;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class CollectionUtils {

    /**
     * If both lists are null, we’ll return true.
     * Or else if only one of them points to a null value or the size() of two lists differ,
     * then we’ll return false.
     * If none of those conditions holds true, we’ll first sort the two lists and then compare them.
     * <p>
     * Note that we have created copies of the two lists to ensure that the elements in the original lists
     * remain untouched.
     */
    public static <T extends Comparable<T>> boolean isEqualsLists(List<T> list1, List<T> list2) {
        if (list1 == null && list2 == null) {
            return true;
        } else if (list1 == null || list2 == null) {
            return false;
        } else if (list1.size() != list2.size()) {
            return false;
        }

        // Copying to avoid rearranging original lists.
        list1 = new ArrayList<>(list1);
        list2 = new ArrayList<>(list2);

        Collections.sort(list1);
        Collections.sort(list2);

        return list1.equals(list2);
    }

    /**
     * If the data in our lists are unique i.e. there isn’t a duplication, we can simply create TreeSets
     * from the given lists and then compare them using equals():
     */
    public static <T extends Comparable<T>> boolean isEqualsUniqueLists(List<T> list1, List<T> list2) {
        if (list1 == null && list2 == null) {
            return true;
        } else if (list1 == null || list2 == null) {
            return false;
        } else if (list1.size() != list2.size()) {
            return false;
        }

        Set<T> set1 = new TreeSet<>(list1);
        Set<T> set2 = new TreeSet<>(list2);

        return set1.equals(set2);
    }

    public static void assertTrue(boolean condition) {
        log.info("It's true: {}", condition);
    }

    public static boolean isThisEquals(List<Integer> list1, List<Integer> list2) {
        log.info("It's true");
        return true;
    }
}
