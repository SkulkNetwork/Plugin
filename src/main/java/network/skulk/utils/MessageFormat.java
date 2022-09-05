package network.skulk.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

import java.util.ArrayList;

public abstract class MessageFormat {

    public static Component fmt(String color, final char symbol, final String text, final String... placeholders) {
        if (color.equalsIgnoreCase("orange")) {
            color = "#ffae1a";
        }

        final var template = "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);

        final var tagResolvers = new ArrayList<TagResolver>();

        // Example: fmt("red", '!', "Unparsed Input: <0>", "<red>hello")
        for (int i = 0; i < placeholders.length; i++) {
            tagResolvers.add(Placeholder.unparsed(Integer.toString(i), placeholders[i]));
        }

        return MiniMessage.miniMessage().deserialize(template, TagResolver.resolver(tagResolvers));
    }
}
