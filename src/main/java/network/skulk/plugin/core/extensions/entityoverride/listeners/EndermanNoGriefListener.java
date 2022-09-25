package network.skulk.plugin.core.extensions.entityoverride.listeners;

import network.skulk.plugin.core.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.wrapper.BaseListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.NotNull;

public final class EndermanNoGriefListener extends BaseListener<EntityOverrideExtension> {
    public EndermanNoGriefListener(final @NotNull EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityChangeBlockEvent(final @NotNull EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
