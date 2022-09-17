package network.skulk.wrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static network.skulk.helpers.MiniMessageHelper.sendMessage;
import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseCommand<E extends BaseExtension> implements CommandExecutor, TabCompleter {
    private final E extension;
    protected int minArgs;
    protected String name;
    protected boolean playerOnly;
    protected int maxArgs;
    protected @Nullable String permission = null;

    public BaseCommand(final @NotNull E extension) {
        this.extension = extension;
        this.init();
        this.extension.getPlugin().registerCommand(this);
    }

    protected @NotNull E getExtension() {
        return this.extension;
    }

    // aliases, playerOnly, marArgs and minArgs will be set here.
    @OverrideOnly
    protected void init() {
    }

    @OverrideOnly
    protected @Nullable ArrayList<String> tabComplete(final @NotNull CommandSender player, final String @NotNull [] args) {
        return null;
    }

    @OverrideOnly
    protected @Nullable ArrayList<String> tabComplete(final @NotNull Player player, final String @NotNull [] args) {
        return null;
    }

    @OverrideOnly
    protected boolean execute(final @NotNull CommandSender sender, final String @NotNull [] args) {
        return false;
    }

    @OverrideOnly
    protected boolean execute(final @NotNull Player player, final String @NotNull [] args) {
        return false;
    }

    @OverrideOnly
    protected boolean execute(final @NotNull CommandSender sender) {
        return false;
    }

    @OverrideOnly
    protected boolean execute(final @NotNull Player player) {
        return false;
    }

    @Override public @Nullable List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (this.playerOnly) {
            return tabComplete((Player) sender, args);

        }
        else {
            return tabComplete(sender, args);
        }
    }

    @Override public final boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (this.playerOnly && !(sender instanceof Player)) {
            sendMessage(sender, "red", '!', "This command can only be used by players.");
            return true;
        }

        if (this.permission != null && !sender.hasPermission(this.permission)) {
            sendMessage(sender, "red", '!', "You can't use this command.");
            return true;
        }

        if (args.length > this.maxArgs || args.length < this.minArgs) {
            return false;
        }

        if (this.playerOnly) {
            final var player = (Player) sender;
            if (maxArgs == 0) {
                return execute(player);
            }
            return execute(player, args);
        }

        if (maxArgs == 0) {
            return execute(sender);
        }
        return execute(sender, args);
    }
}
