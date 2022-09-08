package network.skulk.plugin.extensions.entityoverride;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.entityoverride.listeners.EntityChangeBlockListener;
import network.skulk.plugin.extensions.entityoverride.listeners.EntityDeathListener;
import network.skulk.plugin.extensions.entityoverride.listeners.EntityExplodeListener;
import network.skulk.wrapper.BaseExtension;

public final class EntityOverrideExtension extends BaseExtension {
    public EntityOverrideExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override
    protected void initListeners() {
        new EntityChangeBlockListener(this);
        new EntityDeathListener(this);
        new EntityExplodeListener(this);
    }
}
