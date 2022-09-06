package network.skulk.wrapper;

import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.io.File;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseExtension {
    private BasePlugin plugin;

    public final BaseExtension create(final BasePlugin plugin) {
        this.plugin = plugin;
        return this;
    }

    protected final BasePlugin getPlugin() {
        return this.plugin;
    }


    @OverrideOnly
    protected void initCommands() {
    }

    @OverrideOnly
    protected void initListeners() {
    }

    @OverrideOnly
    protected void onEnableHook() throws Exception {
    }

    @OverrideOnly
    protected void onDisableHook() throws Exception {
    }

    public final void onEnable() throws Exception {
        this.initCommands();
        this.initListeners();
        this.onEnableHook();
    }

    public final void onDisable() throws Exception {
        this.onDisableHook();
    }

    // Utility functions.

    public final File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    public final void registerCommand(final BaseCommand<?> command) {
        this.plugin.registerCommand(command);
    }

    public final void registerListener(final BaseListener<?> listener) {
        this.plugin.registerListener(listener);
    }

    public final BukkitTask runAfter(final long delay, final Runnable runnable) {
        return this.plugin.runAfter(delay, runnable);
    }

    public final BukkitTask runAsync(final Runnable runnable) {
        return this.plugin.runAsync(runnable);
    }

    public final void runRepeatingAsync(final long interval, final Runnable runnable) {
        this.plugin.runRepeatingAsync(interval, runnable);
    }

    protected final void reportError(final String message, @Nullable final Throwable error) {
        this.getPlugin().reportError(message, error);
    }

    protected final void reportError(final String message) {
        this.getPlugin().reportError(message, null);
    }
}
