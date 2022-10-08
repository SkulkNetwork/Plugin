package tk.skulk.plugin.core.extensions.entityoverride.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.entityoverride.EntityOverrideExtension;
import tk.skulk.plugin.wrapper.BaseListener;

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
