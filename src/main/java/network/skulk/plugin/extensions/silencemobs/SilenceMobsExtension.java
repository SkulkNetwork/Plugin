package network.skulk.plugin.extensions.silencemobs;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.silencemobs.listeners.PlayerInteractEntityListener;
import network.skulk.wrapper.BaseExtension;

public final class SilenceMobsExtension extends BaseExtension {
    public SilenceMobsExtension(final Plugin plugin) {
        super(plugin);
    }

    @Override public void initListeners() {
        new PlayerInteractEntityListener(this);
    }
}
