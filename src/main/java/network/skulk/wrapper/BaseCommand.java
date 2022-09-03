package network.skulk.wrapper;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static network.skulk.utils.Fmt.fmt;
import static org.jetbrains.annotations.ApiStatus.OverrideOnly;

public class BaseCommand<E extends BaseExtension> implements CommandExecutor {
    public final BaseCommand<E> create(final E extension) {
        this.extension = extension;
        return this;
    }

    public E extension;
    protected String[] aliases;
    protected boolean playerOnly;
    protected int maxArgs;

    @OverrideOnly
    public void init() {
    }

    @SuppressWarnings("unused")
    @OverrideOnly
    protected boolean execute(final CommandSender sender, final Command command, final String[] args) {
        return false;
    }

    @SuppressWarnings("unused")
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

    public final File getDataFolder() {
        return this.extension.getDataFolder();
    }

    public final void runAfter(final long delay, final Runnable runnable) {
        this.extension.runAfter(delay, runnable);
    }

    public final void runAsync(final Runnable runnable) {
        this.extension.runAsync(runnable);
    }
}
