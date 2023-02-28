package tk.skulk.plugin.core

import org.bukkit.Sound
import org.bukkit.entity.Entity

fun Entity.playTeleportEffect() {
    world.playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 1F, 1F)
}
