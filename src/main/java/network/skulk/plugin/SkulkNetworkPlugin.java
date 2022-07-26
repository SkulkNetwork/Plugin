package network.skulk.plugin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkulkNetworkPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("[SKULK NETWORK PLUGIN] The plugin has been disabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[SKULK NETWORK PLUGIN] The plugin has been disabled.");
    }

    public void register(String name, CommandExecutor executor) {
        PluginCommand command = getCommand(name);

        if (command == null) {
            getLogger().severe("The command '" + name + "' could not be registered because it was not included in the plugin.yml.");
            return;
        }

        command.setExecutor(executor);
    }

    public void runTaskLater(long delay, Runnable runnable) {
        Bukkit.getScheduler().runTaskLater(this, runnable, delay);
    }
}
