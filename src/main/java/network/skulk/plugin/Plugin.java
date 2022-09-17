package network.skulk.plugin;

import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.wrapper.BasePlugin;

// TODO: Make custom maps and solve "for \(final \w+ \w+ : [\w. \(\)]+ \{\n[ ]+if"
public final class Plugin extends BasePlugin {
    @Override
    protected void initExtensions() {
        new EntityOverrideExtension(this);
        new HomesExtension(this);
        new MessageOverrideExtension(this);
        new TPAExtension(this);
    }
}
