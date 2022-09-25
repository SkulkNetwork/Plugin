package network.skulk.plugin.core.extensions.entityoverride.listeners;

import network.skulk.plugin.core.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public final class CreeperNoGriefListener extends BaseListener<EntityOverrideExtension> {
    public CreeperNoGriefListener(final @NotNull EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityExplode(final @NotNull EntityExplodeEvent event) {
        if (event.getEntityType() != EntityType.CREEPER) {
            return;
        }

        event.setCancelled(true);
    }
}
