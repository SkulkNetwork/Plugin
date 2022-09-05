package network.skulk.pluginrewrite.extensions.messageoverride;

import network.skulk.pluginrewrite.extensions.messageoverride.listeners.PlayerDeathListener;
import network.skulk.pluginrewrite.extensions.messageoverride.listeners.PlayerJoinQuitListener;
import network.skulk.pluginrewrite.extensions.messageoverride.listeners.UnknownCommandListener;
import network.skulk.wrapper.BaseExtension;

public final class MessageOverrideExtension extends BaseExtension {
    @Override
    protected void initListeners() {
        new PlayerDeathListener().create(this);
        new PlayerJoinQuitListener().create(this);
        new UnknownCommandListener().create(this);
    }
}
