package network.skulk.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.logging.Level;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BasePlugin extends JavaPlugin {
    private final ArrayList<BaseExtension> extensions = new ArrayList<>();

    @OverrideOnly
    protected Class<BaseExtension>[] extensions() {
        return null;
    }

    @Override
    public final void onEnable() {
        final var logger = this.getLogger();
        logger.info("The plugin is being loaded...");

        for (final Class<BaseExtension> Extension : this.extensions()) {
            final BaseExtension extension;

            try {
                extension = Extension.getDeclaredConstructor().newInstance();
            } catch (final Exception error) {
                this.reportError("Well this should never happen, Failed to init '%s'.\nHere is the traceback:".formatted(Extension.getName()), error);
                continue;
            }

            extension.plugin = this;
            this.extensions.add(extension);

            try {
                extension.onEnable();
            } catch (final Exception error) {
                this.reportError("There was an error while loading '%s'.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
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

    public final void registerCommand(final BaseCommand<?> command) {
        for (final String alias : command.aliases) {
            final var cmd = getCommand(alias);

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
