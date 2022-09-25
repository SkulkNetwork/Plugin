package network.skulk.plugin.core.extensions.messageoverride;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.messageoverride.listeners.PlayerAdvancementDoneListener;
import network.skulk.plugin.core.extensions.messageoverride.listeners.PlayerDeathListener;
import network.skulk.plugin.core.extensions.messageoverride.listeners.PlayerJoinQuitListener;
import network.skulk.plugin.core.extensions.messageoverride.listeners.UnknownCommandListener;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

public final class MessageOverrideExtension extends BaseExtension {
    public MessageOverrideExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new PlayerAdvancementDoneListener(this);
        new PlayerDeathListener(this);
        new PlayerJoinQuitListener(this);
        new UnknownCommandListener(this);
    }
}
