package network.skulk.plugin.extensions.message_override;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.message_override.listeners.PlayerDeathEventListener;
import network.skulk.plugin.extensions.message_override.listeners.PlayerJoinQuitEventListener;

public record MessageOverrideExtension(Plugin plugin) implements BaseExtension {

    public void onEnable() {
        plugin.registerEvent(new PlayerDeathEventListener());
        plugin.registerEvent(new PlayerJoinQuitEventListener());
    }

    public void onDisable() {

    }
}
