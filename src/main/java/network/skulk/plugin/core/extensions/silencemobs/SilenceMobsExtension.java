package network.skulk.plugin.core.extensions.silencemobs;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.silencemobs.listeners.PlayerInteractEntityListener;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;

public final class SilenceMobsExtension extends BaseExtension {
    public SilenceMobsExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new PlayerInteractEntityListener(this);
    }
}
