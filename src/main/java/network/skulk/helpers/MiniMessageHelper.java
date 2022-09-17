package network.skulk.helpers;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public abstract class MiniMessageHelper {
    public static @NotNull Component fmt(final @NotNull String text, final @NotNull String... placeholders) {
        final var tagResolvers = new TagResolver[placeholders.length];

        // Example: sendMessage(player, "red", '!', "Unparsed Input: <0>", "<red>hello");
        for (int i = 0; i < placeholders.length; i++) {
            tagResolvers[i] = Placeholder.unparsed(Integer.toString(i), placeholders[i]);
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }

    public static @NotNull Component makeMessage(final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull String... placeholders) {
        final var template = "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);

        return MiniMessageHelper.fmt(template, placeholders);
    }

    public static void sendMessage(final @NotNull Audience audience, final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull String... placeholders) {
        audience.sendMessage(MiniMessageHelper.makeMessage(color, symbol, text, placeholders));
    }

    public static @NotNull Component mmWithComponent(final @NotNull String text, final @NotNull Component @NotNull ... components) {
        final var tagResolvers = new TagResolver[components.length];

        // Example: fmt("My Component: <0>", event.getMessage());
        for (int i = 0; i < components.length; i++) {
            final var component = components[i];
            tagResolvers[i] = Placeholder.component(Integer.toString(i), component.style(Style.style().build()));
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }

    public static @NotNull Component makeMessageWithComponent(final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull Component... components) {
        final var template = "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);

        return MiniMessageHelper.mmWithComponent(template, components);
    }
}
