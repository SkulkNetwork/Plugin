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

    /*
    MUST be overriden, example:

    @Override
    protected void initExtensions() {
        new MyExtension().init(this);
    }
     */
    @OverrideOnly
    protected void initExtensions() {
    }

    // Loading mechanic.

    @Override
    public final void onEnable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being loaded...");

        this.initExtensions();

        for (final BaseExtension extension : this.extensions) {
            try {
                extension.onEnable();
            } catch (final Exception error) {
                this.reportError("There was an error while loading '%s'. Here is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        logger.info("The plugin has been loaded.");
    }

    @Override
    public final void onDisable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being unloaded...");

        Bukkit.getScheduler().cancelTasks(this);

        for (final BaseExtension extension : this.extensions) {
            try {
                extension.onDisable();
            } catch (final Exception error) {
                this.reportError("There was an error while unloading %s. Here is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        this.extensions.clear();

        logger.info("The plugin has been unloaded.");
    }

    // Public utilities.

    public final void registerExtension(final BaseExtension extension) {
        this.extensions.add(extension);
    }

    public final void registerCommand(final BaseCommand<?> command) {
        final var cmd = this.getCommand(command.name);

        if (cmd != null) {
            cmd.setExecutor(command);
            return;
        }

        this.reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(command.name));
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

    // TODO: don't use deprecated stuff.
    public final void runRepeatingAsync(final long interval, final Runnable runnable) {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, runnable, interval, interval);
    }

    public final void reportError(final String message, final @Nullable Throwable error) {
        if (error == null) {
            this.getLogger().severe(message);

        } else {
            this.getLogger().log(Level.SEVERE, message, error);
        }
    }

    public final void reportError(final String message) {
        this.reportError(message, null);
    }
}
