package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.command.UnknownCommandEvent;
import org.jetbrains.annotations.NotNull;

import static network.skulk.helpers.MiniMessageHelper.makeMessage;

public final class UnknownCommandListener extends BaseListener<MessageOverrideExtension> {
    public UnknownCommandListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUnknownCommand(final @NotNull UnknownCommandEvent event) {
        event.message(makeMessage("red", '!', "Unknown command: <0>", event.getCommandLine()));
    }
}
