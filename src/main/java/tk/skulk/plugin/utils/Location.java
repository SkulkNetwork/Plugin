package tk.skulk.plugin.utils;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class Location {
    private final org.bukkit.Location bukkitLocation;

    public Location(final @NotNull org.bukkit.Location location) {
        this.bukkitLocation = location;
    }

    @SuppressWarnings("unused")
    public static @NotNull Location deserialize(final @NotNull Map<String, Object> map) {
        map.put("world", Bukkit.getWorld((String) map.get("world")));
        return new Location(org.bukkit.Location.deserialize(map));
    }

    public @NotNull HashMap<String, Object> serialize() {
        final var map = new HashMap<String, Object>();
        map.put("world", this.bukkitLocation.getWorld().getName());
        map.put("x", this.bukkitLocation.getX());
        map.put("y", this.bukkitLocation.getY());
        map.put("z", this.bukkitLocation.getZ());
        map.put("yaw", this.bukkitLocation.getYaw());
        map.put("pitch", this.bukkitLocation.getPitch());

        return map;
    }
}
