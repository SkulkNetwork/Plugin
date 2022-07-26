package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.extensions.tpa.TPAExtension;
import network.skulk.plugin.utils.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

public final class TPAIgnoreCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPAIgnoreCommand(@NotNull TPAExtension tpaExtension) {
        extension = tpaExtension;
        extension.register("tpa", this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            Message.sendOnlyPlayer(sender);
            return true;
        } else if (args.length != 1) {
            return false;
        }

        PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        return true;
    }
}
