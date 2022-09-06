package network.skulk.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BasePlugin extends JavaPlugin {
    // Must be stored to do onDisable.
    private final List<BaseExtension> extensions = new ArrayList<>();

    @OverrideOnly
    protected BaseExtension[] initExtensions() {
        return new BaseExtension[0];
    }

    // Public utilities.

    public final void registerCommand(final BaseCommand<?> command) {
        for (final String alias : command.aliases) {
            final var cmd = this.getCommand(alias);

            if (cmd == null) {
                this.reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(alias));
                return;
            }

            cmd.setExecutor(command);
            cmd.setTabCompleter(command);
        }
    }

    public final void registerListener(final BaseListener<?> listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public final BukkitTask runAfter(final long delay, final Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public final BukkitTask runAsync(final Runnable runnable) {
        return Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    // Privates.

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

    // Loading mechanic.
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
                extension.onDisable();
            } catch (final Exception error) {
                this.reportError("There was an error while unloading %s.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        this.extensions.clear();

        logger.info("The plugin has been unloaded.");
    }
}
