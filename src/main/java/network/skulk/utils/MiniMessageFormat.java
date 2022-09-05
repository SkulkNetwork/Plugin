package network.skulk.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;

public abstract class MiniMessageFormat {

    public static void sendMessage(final CommandSender to, String color, final char symbol, final String text, final String... placeholders) {
        if (color.equalsIgnoreCase("orange")) {
            color = "#ffae1a";
        }

        final var template = "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);

        to.sendMessage(MiniMessageFormat.fmt(template, placeholders));
    }

    public static Component fmt(final String text, final String... placeholders) {
        final var tagResolvers = new TagResolver[placeholders.length];

        // Example: sendMessage(player, "red", '!', "Unparsed Input: <0>", "<red>hello");
        for (int i = 0; i < placeholders.length; i++) {
            tagResolvers[i] = Placeholder.unparsed(Integer.toString(i), placeholders[i]);
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }

    public static Component mmWithComponent(final String text, final Component... components) {
        final var tagResolvers = new TagResolver[components.length];

        // Example: fmt("My Component: <0>", event.getMessage());
        for (int i = 0; i < components.length; i++) {
            final var component = components[i];
            tagResolvers[i] = Placeholder.component(Integer.toString(i), component.style(Style.style().build()));
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }
}
