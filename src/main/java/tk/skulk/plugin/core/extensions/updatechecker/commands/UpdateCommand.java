package tk.skulk.plugin.core.extensions.updatechecker.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.updatechecker.UpdateCheckerExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class UpdateCommand extends BaseCommand<UpdateCheckerExtension> {
    public UpdateCommand(final @NotNull UpdateCheckerExtension extension) {
        super(extension);
    }

    @Override
    protected void init() {
        this.setName("update-snp");
        this.setDescription("Updates the plugin.");
        this.setUsage("/update-snp");
        this.setPlayerOnly(false);
        this.setPermission("op");
    }

    // TODO
    @Override
    protected boolean execute(final @NotNull CommandSender sender) {
        sendMessage(sender, "orange", '!', "This command is not implemented yet.");
        return true;
    }
}
