package network.skulk.plugin.extensions.entityoverride;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.entityoverride.listeners.CreeperNoGriefListener;
import network.skulk.plugin.extensions.entityoverride.listeners.DragonDropElytraListener;
import network.skulk.plugin.extensions.entityoverride.listeners.EndermanNoGriefListener;
import network.skulk.plugin.extensions.entityoverride.listeners.ExplosionDropAllListener;
import network.skulk.wrapper.BaseExtension;

public final class EntityOverrideExtension extends BaseExtension {
    public EntityOverrideExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new CreeperNoGriefListener(this);
        new EndermanNoGriefListener(this);
        new DragonDropElytraListener(this);
        new ExplosionDropAllListener(this);
    }
}
