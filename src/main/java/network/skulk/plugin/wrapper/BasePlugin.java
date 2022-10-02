package network.skulk.plugin.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BasePlugin extends JavaPlugin {
    private final ArrayList<BaseExtension> extensions = new ArrayList<>();

    public ArrayList<BaseExtension> getExtensions() {
        return this.extensions;
    }

    /*
     * MUST be overriden, example:
     *
     * @Override protected void initExtensions() {
     *     new MyExtension(this);
     * }
     */
    @OverrideOnly
    protected void initExtensions() {
    }

    @Override public final void onEnable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being loaded...");

        this.initExtensions();

        for (final BaseExtension extension : this.extensions) {
            try {
                extension.onEnable();
            } catch (final Exception error) {
                this.reportError("There was an error while loading '%s':".formatted(extension.getClass().getName()), error);
                this.extensions.remove(extension);
            }
        }

        logger.info("The plugin has been loaded.");
    }

    @Override public final void onDisable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being unloaded...");

        Bukkit.getScheduler().cancelTasks(this);

        for (final BaseExtension extension : this.extensions) {
            try {
                extension.onDisable();
            } catch (final Exception error) {
                this.reportError("There was an error while unloading %s:".formatted(extension.getClass().getName()), error);
            }
        }

        this.extensions.clear();

        logger.info("The plugin has been unloaded.");
    }

    public final @NotNull BukkitTask runAfter(final long delay, final @NotNull Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public final void runAsync(final @NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(this, runnable);
    }

    public final void runSync(final @NotNull Runnable runnable) {
        Bukkit.getScheduler().runTask(this, runnable);
    }

    public final void runRepeatingAsync(final long interval, final @NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskTimer(this, runnable, interval, interval);
    }

    public final void reportError(final @NotNull String message, final @Nullable Throwable error) {
        if (error == null) {
            this.getLogger().severe(message);
        }
        else {
            this.getLogger().log(Level.SEVERE, message, error);
        }
    }

    public final void reportError(final @NotNull String message) {
        this.reportError(message, null);
    }
}
