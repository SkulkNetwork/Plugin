package network.skulk.plugin;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BasePlugin;

public final class Plugin extends BasePlugin {

    @Override
    protected void initExtensions() {
        new EntityOverrideExtension().init(this);
        new HomesExtension().init(this);
        new MessageOverrideExtension().init(this);
        new TPAExtension().init(this);
    }
}
