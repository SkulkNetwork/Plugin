package network.skulk.plugin;

import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.plugin.pdts.BooleanPDT;
import network.skulk.plugin.pdts.StringHashSetPDT;
import network.skulk.plugin.pdts.StringListIncludesPDT;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Plugin extends JavaPlugin {
    // Private attributes.
    private final ArrayList<BaseExtension> extensions = new ArrayList<>();

    public Plugin() {
        extensions.add(new TPAExtension(this));
    }

    // Public methods.
    public void reportError(@NotNull String message) {
        getLogger().severe(message);
    }

    public void reportError(@NotNull String message, @NotNull Throwable error) {
        getLogger().log(Level.SEVERE, message, error);
    }

    public void runLater(long delay, @NotNull Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }

    public void registerCommand(@NotNull CommandExecutor executor, @NotNull String name) {
        PluginCommand command = getCommand(name);

        if (command == null) {
            reportError("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(name));
            return;
        }

        command.setExecutor(executor);
    }

    // Overrides.
    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("[SKULK NETWORK PLUGIN] The plugin is being loaded...");

        for (BaseExtension extension : extensions) {
            try {
                extension.onEnable();
            } catch (Exception error) {
                reportError("[SKULK NETWORK PLUGIN] There was an error while loading '%s'.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        logger.info("[SKULK NETWORK PLUGIN] The plugin has been loaded");
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("[SKULK NETWORK PLUGIN] The plugin is being unloaded...");

        for (BaseExtension extension : extensions) {
            try {
                extension.onDisable();
            } catch (Exception error) {
                reportError("[SKULK NETWORK PLUGIN] There was an error while unloading %s.\nHere is the traceback:".formatted(extension.getClass().getName()), error);
            }
        }

        logger.info("[SKULK NETWORK PLUGIN] The plugin has been unloaded");
    }

    // Public attributes.
    public static final class PersistentDataTypes {
        public static final BooleanPDT BOOLEAN = new BooleanPDT();
        public static final StringHashSetPDT STRING_HASH_SET = new StringHashSetPDT();
        public static final Class<StringListIncludesPDT> STRING_LIST_INCLUDES = StringListIncludesPDT.class;
    }
}
