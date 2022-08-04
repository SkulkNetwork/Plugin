package network.skulk.wrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static network.skulk.pluginrewrite.Utils.fmt;
import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BaseCommand<E extends BaseExtension> implements CommandExecutor {
    protected static boolean playerOnly;
    protected static int maxArgs;

    protected final E extension;

    public BaseCommand(final E extension) {
        this.extension = extension;
        init();
    }

    @OverrideOnly
    protected void init() {
        playerOnly = true;
        maxArgs = 0;
    }

    @SuppressWarnings("unused")
    @OverrideOnly
    protected boolean execute(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String[] args) {
        return false;
    }

    @SuppressWarnings("unused")
    @OverrideOnly
    protected boolean execute(final @NotNull Player player, final @NotNull Command command, final @NotNull String[] args) {
        return false;
    }

    @Override
    public final boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage(fmt("red", "!", "This command can only be used by players."));
            return true;
        }

        if (args.length > maxArgs) {
            return false;
        }

        if (playerOnly) {
            return execute((Player) sender, command, args);
        }

        return execute(sender, command, args);
    }
}
