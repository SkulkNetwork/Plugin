package network.skulk.plugin;

import network.skulk.plugin.extensions.Extension;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public final class SkulkNetworkPlugin extends JavaPlugin {
    private final ArrayList<Extension> extensions = new ArrayList<>();

    public SkulkNetworkPlugin() {
        extensions.add(new TPAExtension(this));
    }

    @Override
    public void onEnable() {
        Logger logger = getLogger();
        logger.info("[SKULK NETWORK PLUGIN] The plugin is being enabled...");

        for (Extension extension : extensions) {
            extension.onEnable();
        }

        logger.info("[SKULK NETWORK PLUGIN] The plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        Logger logger = getLogger();
        logger.info("[SKULK NETWORK PLUGIN] The plugin is being disabled...");

        for (Extension extension : extensions) {
            extension.onDisable();
        }

        logger.info("[SKULK NETWORK PLUGIN] The plugin has been disabled.");
    }

    public void reportError(String action, Exception error) {
        Logger logger = getLogger();
        logger.severe("[SKULK NETWORK PLUGIN] There was an error while %s:".formatted(action));
        logger.severe(Arrays.toString(error.getStackTrace()));
    }

    public void register(String name, CommandExecutor executor) {
        PluginCommand command = getCommand(name);

        if (command == null) {
            getLogger().severe("The command '%s' could not be registered because it was not included in the plugin.yml.".formatted(name));
            return;
        }

        command.setExecutor(executor);
    }

    public void runTaskLater(long delay, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }
}
