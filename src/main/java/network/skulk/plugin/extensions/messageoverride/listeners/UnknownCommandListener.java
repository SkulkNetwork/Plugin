package network.skulk.plugin.extensions.messageoverride.listeners;

import network.skulk.plugin.extensions.messageoverride.MessageOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.command.UnknownCommandEvent;

import static network.skulk.utils.MiniMessageFormat.fmt;

public final class UnknownCommandListener extends BaseListener<MessageOverrideExtension> {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onUnknownCommand(final UnknownCommandEvent event) {
        event.message(fmt("<b><gray>[ <red>!</red> ]</gray></b> <red>Unknown command: <0></red>", event.getCommandLine()));
    }
}
