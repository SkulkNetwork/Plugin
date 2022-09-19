package network.skulk.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

public final class ShortLocation implements Serializable {
    private final String world;
    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;
    private transient @Nullable Location location;

    public ShortLocation(final @NotNull Location l) {
        this.location = l;
        this.world = l.getWorld().getName();
        this.x = l.getX();
        this.y = l.getY();
        this.z = l.getZ();
        this.pitch = l.getPitch();
        this.yaw = l.getYaw();
    }

    public @NotNull Location toLocation() {
        if (this.location == null) {
            this.location = new Location(Objects.requireNonNull(Bukkit.getWorld(this.world)), this.x, this.y, this.z, this.yaw, this.pitch);
        }

        return this.location;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }
}
