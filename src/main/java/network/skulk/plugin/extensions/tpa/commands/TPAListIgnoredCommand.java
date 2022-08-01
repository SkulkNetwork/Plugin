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

public final class TPAListIgnoredCommand implements CommandExecutor {
    private final @NotNull TPAExtension extension;

    public TPAListIgnoredCommand(@NotNull TPAExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "tpa-list-ignored");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length != 0) {
            return false;
        }

        PersistentDataContainer playerContainer = player.getPersistentDataContainer();

        HashSet<String> playerIgnores = playerContainer.getOrDefault(extension.TPA_IGNORES_KEY, Plugin.PersistentDataTypes.STRING_HASH_SET, new HashSet<>());

        if (playerIgnores.size() == 0) {
            player.sendRichMessage(Message.NO_TPA_IGNORES);
            return true;
        }

        if (playerIgnores.contains(TPAExtension.IGNORE_ALL_SYMBOL)) {
            player.sendRichMessage(Message.TPA_IGNORING_ALL);

        } else {
            StringBuilder response = new StringBuilder(Message.TPA_IGNORING_FOLLOWING);

            for (String ignoredName : playerIgnores) {
                response.append(Message.TPA_IGNORING_FOLLOWING_ITEM.formatted(ignoredName));
            }

            player.sendRichMessage(response.toString());
        }

        return true;
    }
}
