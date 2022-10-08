package tk.skulk.plugin.core.extensions.silencemobs;

import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.Plugin;
import tk.skulk.plugin.core.extensions.silencemobs.listeners.PlayerInteractEntityListener;
import tk.skulk.plugin.wrapper.BaseExtension;

public final class SilenceMobsExtension extends BaseExtension {
    public SilenceMobsExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initListeners() {
        new PlayerInteractEntityListener(this);
    }
}
