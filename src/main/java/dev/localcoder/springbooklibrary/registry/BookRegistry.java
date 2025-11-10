package dev.localcoder.springbooklibrary.registry;

import dev.localcoder.springbooklibrary.entity.Book;

import java.util.HashMap;
import java.util.Map;

public class BookRegistry {
    private static final Map<String, Book> prototyps = new HashMap<>();

    public static void addPrototype(String key, Book book) {
        prototyps.put(key, book);
    }

    public static Book getPrototype(String key) throws IllegalAccessException {
        Book prototype = prototyps.get(key);
        if (prototype == null) {
            throw new IllegalAccessException("Prototype is not available " + key);
        }
        return prototype.deepCopy();
    }

    public static boolean contains(String key) {
        return prototyps.containsKey(key);
    }
}
