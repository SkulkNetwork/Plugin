package network.skulk.utils;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public final class UUID2CaseInsensitiveMap<V> extends HashMap<UUID, TreeMap<String, V>> {
    public TreeMap<String, V> get(final UUID key) {
        return this.computeIfAbsent(key, k -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
    }
}
