package network.skulk.helpers;

import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;

public abstract class EffectHelper {
    public static void playTeleport(final Entity entity) {
        final var world = entity.getWorld();
        final var location = entity.getLocation();
        world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1, 1);
        world.spawnParticle(Particle.PORTAL, location, 100);
    }
}
