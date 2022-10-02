package network.skulk.plugin.core.extensions.updatechecker;

import network.skulk.plugin.core.Plugin;
import network.skulk.plugin.core.extensions.updatechecker.commands.UpdateCommand;
import network.skulk.plugin.core.extensions.updatechecker.listeners.OpJoinListener;
import network.skulk.plugin.wrapper.BaseExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UpdateCheckerExtension extends BaseExtension {

    public UpdateCheckerExtension(final @NotNull Plugin plugin) {
        super(plugin);
    }

    @Override protected void initCommands() {
        new UpdateCommand(this);
    }

    @Override protected void initListeners() {
        new OpJoinListener(this);
    }

    // TODO
    public @Nullable String getLatestVersion() {
        return null;
    }
}
