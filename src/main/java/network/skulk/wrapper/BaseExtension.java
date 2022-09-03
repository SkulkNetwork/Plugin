package network.skulk.wrapper;

import java.io.File;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BaseExtension {
    public final BaseExtension create(final BasePlugin plugin) {
        this.plugin = plugin;
        this.plugin.extensions.add(this);
        return this;
    }

    public BasePlugin plugin;

    @OverrideOnly
    protected Class<BaseCommand<BaseExtension>>[] commands() {
        return null;
    }

    @OverrideOnly
    protected Class<BaseListener<BaseExtension>>[] listeners() {
        return null;
    }

    @OverrideOnly
    public void onEnableHook() throws Exception {
    }

    @OverrideOnly
    public void onDisableHook() throws Exception {
    }

    public final void onEnable() throws Exception {
        final var commands = this.commands();

        if (commands != null) {
            for (final Class<BaseCommand<BaseExtension>> Command : commands) {
                Command.getDeclaredConstructor().newInstance().create(this);
            }
        }

        final var listeners = this.listeners();

        if (listeners != null) {
            for (final Class<BaseListener<BaseExtension>> Listener : listeners) {
                Listener.getDeclaredConstructor().newInstance().create(this);
            }
        }

        this.onEnableHook();
    }

    public final File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    public final void registerCommand(final BaseCommand<?> command) {
        this.plugin.registerCommand(command);
    }

    public final void registerListener(final BaseListener<?> listener) {
        this.plugin.registerListener(listener);
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        this.plugin.runAfter(delay, runnable);
    }

    public final void runAsync(final Runnable runnable) {
        this.plugin.runAsync(runnable);
    }
}
