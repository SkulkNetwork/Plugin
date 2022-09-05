package network.skulk.wrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import static network.skulk.utils.MiniMessageFormat.sendMessage;
import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public abstract class BaseCommand<E extends BaseExtension> implements CommandExecutor {
    protected int minArgs;
    protected String[] aliases;
    protected boolean playerOnly;
    protected int maxArgs;
    private E extension;

    public final void create(final E extension) {
        this.extension = extension;
        this.init();
        this.extension.registerCommand(this);
    }

    protected E getExtension() {
        return this.extension;
    }

    // aliases, playerOnly, marArgs and minArgs will be set here.
    @OverrideOnly
    protected void init() {
    }

    @OverrideOnly
    protected boolean execute(final CommandSender sender, final String[] args) {
        return false;
    }

    @OverrideOnly
    protected boolean execute(final Player player, final String[] args) {
        return false;
    }

    @Override
    public final boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (playerOnly && !(sender instanceof Player)) {
            sendMessage(sender, "red", '!', "This command can only be used by players.");
            return true;
        }

        if (args.length > maxArgs || args.length < minArgs) {
            return false;
        }

        if (playerOnly) {
            return execute((Player) sender, args);
        }

        return execute(sender, args);
    }

    // Utility functions.

    public final BukkitTask runAfter(final long delay, final Runnable runnable) {
        return this.extension.runAfter(delay, runnable);
    }

    public final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }
}
