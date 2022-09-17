package network.skulk.plugin.extensions.entityoverride.listeners;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.NotNull;

public final class ExplosionDropAllListener extends BaseListener<EntityOverrideExtension> {
    public ExplosionDropAllListener(final @NotNull EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onEntityExplode(final @NotNull EntityExplodeEvent event) {
        event.setYield(100);
    }
}
