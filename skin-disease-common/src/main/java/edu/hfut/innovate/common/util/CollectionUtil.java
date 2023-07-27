package edu.hfut.innovate.common.util;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : Chowhound
 * @since : 2023/7/27 - 15:31
 */
public class CollectionUtil {

    public static <K, V, E> Map<K, V> getMap(Collection<E> entities, Function<E, K> keyFunction, Function<E, V> valueFunction) {
        return entities.stream().collect(Collectors.toMap(keyFunction, valueFunction));
    }

    public static <K, E> Map<K, E> getMap(Collection<E> entities, Function<E, K> keyFunction) {
        return getMap(entities, keyFunction, Function.identity());
    }

    public static <E, V> Collection<V> getCollection(Collection<E> entities, Function<E, V> valueFunction) {
        return entities.stream().map(valueFunction).collect(Collectors.toSet());
    }
}
