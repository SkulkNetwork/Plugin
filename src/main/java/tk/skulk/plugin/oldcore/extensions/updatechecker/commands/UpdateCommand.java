package tk.skulk.plugin.oldcore.extensions.updatechecker.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.updatechecker.UpdateCheckerExtension;
import tk.skulk.plugin.wrapper.BaseCommand;

import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class UpdateCommand extends BaseCommand<UpdateCheckerExtension> {
    public UpdateCommand(final @NotNull UpdateCheckerExtension extension) {
        super(extension);
    }

    @Override
    private void init() {
        this.setName("update-snp");
        this.setDescription("Updates the plugin.");
        this.setUsage("/update-snp");
        this.setPlayerOnly(false);
        this.setPermission("op");
    }

    // TODO
    @Override
    private boolean execute(final @NotNull CommandSender sender) {
        sendMessage(sender, "gold", '!', "This command is not implemented yet.");
        return true;
    }
}
