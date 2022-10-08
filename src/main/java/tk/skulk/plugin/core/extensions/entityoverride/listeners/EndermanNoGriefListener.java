package tk.skulk.plugin.core.extensions.entityoverride.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.entityoverride.EntityOverrideExtension;
import tk.skulk.plugin.wrapper.BaseListener;

public final class EndermanNoGriefListener extends BaseListener<EntityOverrideExtension> {
    public EndermanNoGriefListener(final @NotNull EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityChangeBlockEvent(final @NotNull EntityChangeBlockEvent event) {
        if (event.getEntityType() != EntityType.ENDERMAN) {
            return;
        }

        event.setCancelled(true);
    }
}
