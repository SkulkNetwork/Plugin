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

public final class TPAIgnoreCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPAIgnoreCommand(@NotNull final TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-ignore");
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length != 1) {
            return false;
        }

        final String targetName = args[0];

        final PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        final HashSet<String> playerIgnores = playerContainer.getOrDefault(extension.TPA_IGNORES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        if (playerIgnores.contains(TPAExtension.IGNORE_ALL_SYMBOL)) {
            player.sendRichMessage(Message.ALREADY_TPA_IGNORING_ALL);
            return true;
        }

        if (playerIgnores.contains(targetName)) {
            playerIgnores.remove(targetName);
            player.sendRichMessage(Message.UN_TPA_IGNORED_X.formatted(targetName));

        } else {
            playerIgnores.add(targetName);
            player.sendRichMessage(Message.TPA_IGNORED_X.formatted(targetName));
        }

        playerContainer.set(extension.TPA_IGNORES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, playerIgnores);

        return true;
    }
}
