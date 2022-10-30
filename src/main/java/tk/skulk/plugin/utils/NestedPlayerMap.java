package tk.skulk.plugin.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class NestedPlayerMap <V> extends HashMap<Player, HashMap<Player, V>> {
    public @NotNull HashMap<@NotNull Player, V> get(final @NotNull Player key) {
        return this.computeIfAbsent(key, k -> new HashMap<>());
    }
}
