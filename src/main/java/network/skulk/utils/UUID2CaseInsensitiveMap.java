package network.skulk.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.UUID;

public final class UUID2CaseInsensitiveMap<V> extends HashMap<UUID, TreeMap<String, V>> {
    public @NotNull TreeMap<@NotNull String, @Nullable V> get(final @NotNull UUID key) {
        return this.computeIfAbsent(key, k -> new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
    }
}
