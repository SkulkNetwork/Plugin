package network.skulk.plugin.core.extensions.entityoverride;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.entityoverride.listeners.CreeperNoGriefListener;
import network.skulk.plugin.core.extensions.entityoverride.listeners.DragonDropElytraListener;
import network.skulk.plugin.core.extensions.entityoverride.listeners.EndermanNoGriefListener;
import network.skulk.plugin.core.extensions.entityoverride.listeners.ExplosionDropAllListener;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

public final class EntityOverrideExtension extends BaseExtension {
    public EntityOverrideExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CreeperNoGriefListener(this);
        new DragonDropElytraListener(this);
        new EndermanNoGriefListener(this);
        new ExplosionDropAllListener(this);
    }
}
