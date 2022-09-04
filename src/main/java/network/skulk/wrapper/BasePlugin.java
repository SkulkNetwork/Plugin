package network.skulk.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BasePlugin extends JavaPlugin {
    // Must be stored to do onDisable.
    public final ArrayList<BaseExtension> extensions = new ArrayList<>();

    @OverrideOnly
    protected BaseExtension[] initExtensions() {
        return new BaseExtension[]{};
    }

    @Override
    public final void onEnable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being loaded...");

        for (final BaseExtension extension : this.initExtensions()) {
            try {
                extension.onEnable();
            } catch (final Exception error) {
                this.reportError("There was an error while loading '%s'.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
                continue;
            }

            this.extensions.add(extension);
        }

        logger.info("The plugin has been loaded");
    }

    @Override
    public final void onDisable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being unloaded...");

        for (final BaseExtension extension : this.extensions) {
            try {
                extension.onDisableHook();
            } catch (final Exception error) {
                this.reportError("There was an error while unloading %s.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        this.extensions.clear();

        logger.info("The plugin has been unloaded.");
    }

    // Utility functions.

    public final void registerCommand(final BaseCommand<?> command) {
        for (final String alias : command.aliases) {
            final var cmd = this.getCommand(alias);

            if (cmd == null) {
                this.reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(alias));
                return;
            }

            cmd.setExecutor(command);
        }
    }

    public final void registerListener(final BaseListener<?> listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public final void runAsync(final Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    private void reportError(final String message, final @Nullable Throwable error) {
        if (error == null) {
            this.getLogger().severe(message);
        } else {
            this.getLogger().log(Level.SEVERE, message, error);
        }
    }

    private void reportError(final String message) {
        this.reportError(message, null);
    }
}
