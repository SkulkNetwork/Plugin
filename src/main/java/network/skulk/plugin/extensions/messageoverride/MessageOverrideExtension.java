package network.skulk.plugin.extensions.messageoverride;

import network.skulk.plugin.extensions.messageoverride.listeners.PlayerDeathListener;
import network.skulk.plugin.extensions.messageoverride.listeners.PlayerJoinQuitListener;
import network.skulk.plugin.extensions.messageoverride.listeners.UnknownCommandListener;
import network.skulk.wrapper.BaseExtension;

public final class MessageOverrideExtension extends BaseExtension {
    @Override
    protected void initListeners() {
        new PlayerDeathListener().init(this);
        new PlayerJoinQuitListener().init(this);
        new UnknownCommandListener().init(this);
    }
}
