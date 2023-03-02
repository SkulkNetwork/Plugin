package tk.skulk.plugin.oldcore.extensions.messageoverride.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.command.UnknownCommandEvent;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.oldcore.extensions.messageoverride.MessageOverrideExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.makeMessage;

public final class UnknownCommandListener extends BaseListener<MessageOverrideExtension> {
    public UnknownCommandListener(final @NotNull MessageOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onUnknownCommand(final @NotNull UnknownCommandEvent event) {
        event.message(makeMessage("red", '!', "Unknown command: <0>", event.getCommandLine()));
    }
}
