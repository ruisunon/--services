package com.rycoding.ecommerce.utils;

import org.springframework.util.SerializationUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StringToMap {
    public static Map<String, String> convertWithSteam(String mapAsString) {
        Map<String, String> map = Arrays.stream(mapAsString.split(",")).map(entry -> entry.split("="))
                .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        return map;
    }
    public static <String, Object> HashMap<String, Object> deepCopy(HashMap<String, Object> originalMap) {
        return SerializationUtils.clone(originalMap);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    public static <T> List<T> distinctList(List<T> list, Function<? super T, ?>... keyExtractors) {

        return list.stream().filter(distinctByKeys(keyExtractors))
                .collect(Collectors.toList());
    }

    private static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyExtractors) {

        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();

        return t -> {

            final List<?> keys = Arrays.stream(keyExtractors)
                    .map(ke -> ke.apply(t))
                    .collect(Collectors.toList());

            return seen.putIfAbsent(keys, Boolean.TRUE) == null;

        };

    }
}
