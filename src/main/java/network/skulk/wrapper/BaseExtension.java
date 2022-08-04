package network.skulk.wrapper;

import java.io.File;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BaseExtension {
    private final BasePlugin plugin;

    public BaseExtension(final BasePlugin plugin) {
        this.plugin = plugin;
    }

    public final File getDataFolder() {
        return plugin.getRealDataFolder();
    }

    public final void registerCommand(final BaseCommand<?> executor, final String... aliases) {
        plugin.registerCommand(executor, aliases);
    }

    public void registerEvent(final BaseListener<?> listener) {
        plugin.registerEvent(listener);
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        plugin.runAfter(delay, runnable);
    }

    @OverrideOnly
    public void onEnable() throws Exception {
    }

    @OverrideOnly
    public void onDisable() throws Exception {
    }
}
