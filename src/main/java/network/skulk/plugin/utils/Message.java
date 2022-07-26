package network.skulk.plugin.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class Message {
    public static void sendPlayerOffline(@NotNull Player user) {
        user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This player is not online.");
    }

    public static void sendOnlyPlayer(@NotNull CommandSender user) {
        user.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This command can only be used by players.");
    }
}
