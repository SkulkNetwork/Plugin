package network.skulk.plugin;

import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BaseExtension;
import network.skulk.wrapper.BasePlugin;

public final class Plugin extends BasePlugin {

    @Override
    protected BaseExtension[] initExtensions() {
        return new BaseExtension[]{
                new HomesExtension().create(this),
                new MessageOverrideExtension().create(this),
                new TPAExtension().create(this)
        };
    }
}
