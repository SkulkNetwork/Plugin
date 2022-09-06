package network.skulk.plugin.extensions.entityoverride.listeners;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class EntityExplodeListener extends BaseListener<EntityOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityExplode(final EntityExplodeEvent event) {
        event.setYield(100);
    }
}
