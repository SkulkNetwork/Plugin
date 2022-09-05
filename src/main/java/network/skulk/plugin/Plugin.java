package network.skulk.plugin;

import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.extensions.message_override.MessageOverrideExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.plugin.pdts.BooleanPDT;
import network.skulk.plugin.pdts.StringHashSetPDT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    // Private attributes.
    private final ArrayList<BaseExtension> extensions = new ArrayList<>();

    public Plugin() {
        extensions.add(new HomesExtension(this));
        extensions.add(new MessageOverrideExtension(this));
        extensions.add(new TPAExtension(this));
    }

    // Public methods.
    public void reportError(@NotNull final String message) {
        getLogger().severe(message);
    }

    public void reportError(@NotNull final String message, @NotNull final Throwable error) {
        getLogger().log(Level.SEVERE, message, error);
    }

    public void runLater(final long delay, @NotNull final Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public void registerCommand(@NotNull final CommandExecutor executor, @NotNull final String name) {
        final PluginCommand command = getCommand(name);

        if (command == null) {
            reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(name));
            return;
        }

        command.setExecutor(executor);
    }

    public void registerEvent(final Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    // Overrides.
    @Override
    public void onEnable() {
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
    public void onDisable() {
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

    // Public attributes.
    public static final class PersistentDataTypes {
        public static final BooleanPDT BOOLEAN = new BooleanPDT();
        public static final StringHashSetPDT STRING_HASH_SET = new StringHashSetPDT();
    }
}
