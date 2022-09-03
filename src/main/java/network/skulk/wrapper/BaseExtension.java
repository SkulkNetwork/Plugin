package network.skulk.wrapper;

import java.io.File;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BaseExtension {
    public BasePlugin plugin;

    @OverrideOnly
    protected Class<BaseCommand<? extends BaseExtension>>[] commands() {
        return null;
    }

    @OverrideOnly
    protected Class<BaseListener<? extends BaseExtension>>[] listeners() {
        return null;
    }

    public final void onEnable() throws Exception {
        final var commands = this.commands();

        if (commands != null) {
            for (final Class<BaseCommand<? extends BaseExtension>> Command : commands) {
                final var command = Command.getDeclaredConstructor().newInstance();

                command.extension = this;

                this.registerCommand(command);
            }
        }

        final var listeners = this.listeners();

        if (listeners != null) {
            for (final Class<BaseListener<? extends BaseExtension>> Listener : listeners) {
                final var listener = Listener.getDeclaredConstructor().newInstance();

                listener.extension = this;

                this.registerListener(listener);
            }
        }

        this.onEnableHook();
    }

    @OverrideOnly
    public final void onEnableHook() {
    }

    @OverrideOnly
    public final void onDisableHook() {
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
