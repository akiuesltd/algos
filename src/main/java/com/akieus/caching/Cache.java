package com.akieus.caching;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache<K, V> {

    private Map<K, V> map = new ConcurrentHashMap<K, V>();

    public V get(K key) {
        return map.get(key);
    }

    public V put(K key, V value) {
        return map.put(key, value);
    }
}
