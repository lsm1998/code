package com.lsm1998.algorithm;

import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * 实现一个基于LRU的缓存
 * @param <K>
 * @param <V>
 */
public class LruCache<K, V> implements Iterable<K>
{
    private final static int MAX_CACHE_SIZE = 5;

    private final LinkedHashMap<K, V> linkedHashMap;

    public LruCache()
    {
        this.linkedHashMap = new LinkedHashMap<>();
    }

    public void cache(K key, V value)
    {
        linkedHashMap.remove(key);
        linkedHashMap.put(key, value);
        if (linkedHashMap.size() > MAX_CACHE_SIZE)
        {
            var iterator = linkedHashMap.keySet().iterator();
            linkedHashMap.remove(iterator.next());
        }
    }

    @Override
    public Iterator<K> iterator()
    {
        var iterator = linkedHashMap.keySet().iterator();
        return new Iterator<K>()
        {
            @Override
            public boolean hasNext()
            {
                return iterator.hasNext();
            }

            @Override
            public K next()
            {
                return iterator.next();
            }
        };
    }

    public static void main(String[] args)
    {
        LruCache<String, Object> cache = new LruCache<>();
        cache.cache("1", 1);
        cache.cache("2", 2);
        cache.cache("3", 3);
        cache.cache("4", 4);
        cache.cache("5", 5);
        cache.cache("6", 6);
        cache.cache("7", 7);

        for (String s : cache)
        {
            System.out.println(s);
        }
    }
}
