package network.skulk.plugin.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class Location extends org.bukkit.Location {
    public Location(final @NotNull org.bukkit.Location location) {
        super(location.getWorld(), location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @SuppressWarnings("unused")
    public static @NotNull Location deserialize(final @NotNull Map<String, Object> map) {
        map.put("world", Bukkit.getWorld((String) map.get("world")));
        return new Location(org.bukkit.Location.deserialize(map));
    }

    @Override public @NotNull HashMap<String, Object> serialize() {
        final var map = new HashMap<String, Object>();

        map.put("world", this.getWorld().getName());
        map.put("x", this.getX());
        map.put("y", this.getY());
        map.put("z", this.getZ());
        map.put("yaw", this.getYaw());
        map.put("pitch", this.getPitch());

        return map;
    }
}
