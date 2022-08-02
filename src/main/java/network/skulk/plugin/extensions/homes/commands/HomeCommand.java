package network.skulk.plugin.extensions.homes.commands;

import network.skulk.plugin.constants.Message;
import network.skulk.plugin.extensions.homes.Home;
import network.skulk.plugin.extensions.homes.HomesExtension;
import network.skulk.plugin.pdts.HomePDT;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class HomeCommand implements CommandExecutor {
    private final @NotNull HomesExtension extension;

    public HomeCommand(@NotNull HomesExtension extension) {
        this.extension = extension;
        extension.plugin.registerCommand(this, "home");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage(Message.ONLY_PLAYERS_CAN_USE_THIS_COMMAND);
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        String homeName;

        if (args.length == 0) {
            homeName = "home";
        } else {
            homeName = args[0];
        }

        @Nullable Home home = player.getPersistentDataContainer().getOrDefault(extension.HOMES_KEY, new HomePDT(homeName), null);

        if (home == null) {
            player.sendRichMessage(Message.NO_SUCH_HOME.formatted(homeName));
            return true;
        }

        player.sendRichMessage(Message.TELEPORTING_YOU_TO_X.formatted(home.name()));
        player.teleport(home.location());

        return true;
    }
}