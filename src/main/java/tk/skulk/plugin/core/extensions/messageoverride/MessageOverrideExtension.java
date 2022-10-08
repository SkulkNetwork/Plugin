package tk.skulk.plugin.core.extensions.messageoverride;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.core.extensions.messageoverride.listeners.PlayerAdvancementDoneListener;
import tk.skulk.plugin.core.extensions.messageoverride.listeners.PlayerDeathListener;
import tk.skulk.plugin.core.extensions.messageoverride.listeners.PlayerJoinQuitListener;
import tk.skulk.plugin.core.extensions.messageoverride.listeners.UnknownCommandListener;
import tk.skulk.plugin.wrapper.BaseExtension;

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
