package com.akieus.caching;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class LRUCache<K, V> {

    private int size = -1;
    private Map<K, V> map = null;
    private ConcurrentLinkedQueue<K> queue = null;

    public LRUCache(int size) {
        map = new ConcurrentHashMap<K, V>();
        queue = new ConcurrentLinkedQueue<K>();
    }

    public V get(K key) {
        return map.get(key);
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            queue.remove(key);
        }

        while (queue.size() >= size) {
            K oldKey = queue.poll();
            if (oldKey != null) {
                map.remove(oldKey);
            }
        }

        queue.add(key);
        map.put(key, value);
    }
}
