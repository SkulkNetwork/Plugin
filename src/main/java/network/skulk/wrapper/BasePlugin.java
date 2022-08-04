package network.skulk.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BasePlugin extends JavaPlugin {
    private final ArrayList<BaseExtension> extensions = new ArrayList<>();

    public BasePlugin() {
        Collections.addAll(extensions, extensions());
    }

    @OverrideOnly
    protected BaseExtension[] extensions() {
        return null;
    }

    private void reportError(final String message, final @Nullable Throwable error) {
        if (error == null) {
            getLogger().severe(message);
        } else {
            getLogger().log(Level.SEVERE, message, error);
        }
    }

    private void reportError(final String message) {
        reportError(message, null);
    }

    public final File getRealDataFolder() {
        final File dataFolder = new File(getDataFolder(), "data");
        //noinspection ResultOfMethodCallIgnored
        dataFolder.mkdirs();
        return dataFolder;
    }

    public final void registerCommand(final BaseCommand<?> executor, final String... aliases) {
        for (final String alias : aliases) {
            final PluginCommand command = getCommand(alias);

            if (command == null) {
                reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(alias));
                return;
            }

            command.setExecutor(executor);
        }
    }

    public final void registerEvent(final BaseListener<?> listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    @Override
    public final void onEnable() {
        final Logger logger = getLogger();
        logger.info("The plugin is being loaded...");

        for (final BaseExtension extension : extensions) {
            try {
                extension.onEnable();
            } catch (final Exception error) {
                reportError("There was an error while loading '%s'.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        logger.info("The plugin has been loaded");
    }

    @Override
    public final void onDisable() {
        final Logger logger = getLogger();
        logger.info("The plugin is being unloaded...");

        for (final BaseExtension extension : extensions) {
            try {
                extension.onDisable();
            } catch (final Exception error) {
                reportError("There was an error while unloading %s.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        logger.info("The plugin has been unloaded");
    }
}
