package network.skulk.plugin.extensions.entityoverride.listeners;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityChangeBlockEvent;

public final class EntityChangeBlockListener extends BaseListener<EntityOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEnderManGrief(final EntityChangeBlockEvent event) {
        if (event.getEntityType() == EntityType.ENDERMAN) {
            event.setCancelled(true);
        }
    }
}
