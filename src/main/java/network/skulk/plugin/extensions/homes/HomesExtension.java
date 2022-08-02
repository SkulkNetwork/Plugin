package network.skulk.plugin.extensions.homes;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.extensions.BaseExtension;
import network.skulk.plugin.extensions.homes.commands.HomeCommand;
import network.skulk.plugin.extensions.homes.commands.HomeDeleteCommand;
import network.skulk.plugin.extensions.homes.commands.HomeListCommand;
import network.skulk.plugin.extensions.homes.commands.HomeSetCommand;

public class HomesExtension implements BaseExtension {
    public final Plugin plugin;

    public HomesExtension(Plugin plugin) {
        this.plugin = plugin;
    }

    public void onEnable() {
        new HomeCommand(this);
        new HomeDeleteCommand(this);
        new HomeListCommand(this);
        new HomeSetCommand(this);
    }

    public void onDisable() {

    }
}
