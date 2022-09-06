package network.skulk.plugin.extensions.entityoverride;

import network.skulk.plugin.extensions.entityoverride.listeners.EntityChangeBlockListener;
import network.skulk.plugin.extensions.entityoverride.listeners.EntityDeathListener;
import network.skulk.plugin.extensions.entityoverride.listeners.EntityExplodeListener;
import network.skulk.wrapper.BaseExtension;

public final class EntityOverrideExtension extends BaseExtension {

    @Override
    protected void initListeners() {
        new EntityChangeBlockListener().init(this);
        new EntityDeathListener().init(this);
        new EntityExplodeListener().init(this);
    }
}
