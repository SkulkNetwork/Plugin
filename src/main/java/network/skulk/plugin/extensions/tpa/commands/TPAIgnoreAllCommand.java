package network.skulk.plugin.extensions.tpa.commands;

import network.skulk.plugin.Plugin;
import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.tpa.TPAExtension;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public final class TPAIgnoreAllCommand implements CommandExecutor {

    private final @NotNull TPAExtension extension;

    public TPAIgnoreAllCommand(@NotNull TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-ignore");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_ALLOWED);
            return true;
        }

        if (args.length != 0) {
            return false;
        }

        PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        HashSet<String> playerIgnores = playerContainer.getOrDefault(extension.TPA_IGNORES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        String message;

        if (playerIgnores.contains(TPAExtension.IGNORE_ALL_SYMBOL)) {
            playerIgnores.clear(); // I could just do .remove(IGNORE_ALL_SYMBOL); but this is better.
            message = Message.UN_TPA_IGNORED_ALL;

        } else {
            playerIgnores.clear();
            playerIgnores.add(TPAExtension.IGNORE_ALL_SYMBOL);
            message = Message.TPA_IGNORED_ALL;
        }

        playerContainer.set(extension.TPA_IGNORES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, playerIgnores);
        player.sendRichMessage(message);

        return true;
    }
}
