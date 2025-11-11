package dev.localcoder.springbooklibrary.registry;

import dev.localcoder.springbooklibrary.entity.BookEntity;

import java.util.HashMap;
import java.util.Map;

public class BookRegistry {
    private static final Map<String, BookEntity> prototyps = new HashMap<>();

    public static void addPrototype(String key, BookEntity book) {
        prototyps.put(key, book);
    }

    public static BookEntity getPrototype(String key) throws IllegalAccessException {
        BookEntity prototype = prototyps.get(key);
        if (prototype == null) {
            throw new IllegalAccessException("Prototype is not available " + key);
        }
        return prototype.deepCopy();
    }

    public static boolean contains(String key) {
        return prototyps.containsKey(key);
    }
}
