package network.skulk.wrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static network.skulk.utils.MessageFormat.fmt;
import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseCommand<E extends BaseExtension> implements CommandExecutor {
    public E extension;
    protected String[] aliases;
    protected boolean playerOnly;
    protected int maxArgs;

    public final void create(final E extension) {
        this.extension = extension;
        this.extension.registerCommand(this);
    }

    // aliases, playerOnly and marArgs will be set here
    @OverrideOnly
    public void init() {
    }

    @OverrideOnly
    protected boolean execute(final CommandSender sender, final Command command, final String[] args) {
        return false;
    }

    @OverrideOnly
    protected boolean execute(final Player player, final Command command, final String[] args) {
        return false;
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sender.sendMessage(fmt("red", '!', "This command can only be used by players."));
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

    // Utility functions.

    public final void runAfter(final long delay, final Runnable runnable) {
        this.extension.runAfter(delay, runnable);
    }

    public final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }
}
