package network.skulk.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;

public final class NestedPlayerMap<V> extends HashMap<Player, HashMap<Player, V>> {
    public HashMap<Player, V> get(final Player key) {
        return this.computeIfAbsent(key, k -> new HashMap<>());
    }
}
