package network.skulk.wrapper;

import org.bukkit.scheduler.BukkitTask;

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
    public void onEnableHook() throws Exception {
    }

    @OverrideOnly
    public void onDisableHook() throws Exception {
    }

    public final void onEnable() throws Exception {
        this.initCommands();
        this.initListeners();
        this.onEnableHook();
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

    public final void runAsync(final Runnable runnable) {
        this.plugin.runAsync(runnable);
    }
}