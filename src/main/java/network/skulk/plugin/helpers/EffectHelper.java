package network.skulk.plugin.helpers;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public abstract class EffectHelper {
    public static void playTeleport(final @NotNull Entity entity) {
        entity.getWorld().playSound(entity.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
    }
}
