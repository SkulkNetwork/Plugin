package network.skulk.plugin.core.extensions.updatechecker.commands;

import network.skulk.plugin.core.extensions.updatechecker.UpdateCheckerExtension;
import network.skulk.plugin.wrapper.BaseCommand;
import org.jetbrains.annotations.NotNull;

// TODO
public final class UpdateCommand extends BaseCommand<UpdateCheckerExtension> {
    public UpdateCommand(final @NotNull UpdateCheckerExtension extension) {
        super(extension);
    }
}
