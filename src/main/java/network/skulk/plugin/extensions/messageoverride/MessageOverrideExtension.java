package network.skulk.plugin.extensions.messageoverride;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.messageoverride.listeners.PlayerDeathListener;
import network.skulk.plugin.extensions.messageoverride.listeners.PlayerJoinQuitListener;
import network.skulk.plugin.extensions.messageoverride.listeners.UnknownCommandListener;
import network.skulk.wrapper.BaseExtension;

public final class MessageOverrideExtension extends BaseExtension {
    public MessageOverrideExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new PlayerDeathListener(this);
        new PlayerJoinQuitListener(this);
        new UnknownCommandListener(this);
    }
}
