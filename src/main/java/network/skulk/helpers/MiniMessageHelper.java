package network.skulk.helpers;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.jetbrains.annotations.NotNull;

public abstract class MiniMessageHelper {
    private static final String MESSAGE_TEMPLATE = "<b><gray>[ <color:%s>%s</color> ]</gray></b> <color:%s>%s</color>";

    public static void sendMessage(final @NotNull Audience audience, final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull String... placeholders) {
        audience.sendMessage(
                MiniMessageHelper.makeMessage(color, symbol, text, placeholders)
        );
    }

    public static @NotNull Component makeMessage(final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull String... placeholders) {
        return MiniMessageHelper.fmt(
                MiniMessageHelper.MESSAGE_TEMPLATE.formatted(color, symbol, color, text),
                placeholders
        );
    }

    // Example: fmt("Unparsed Input: <0>", "<red>hello");
    public static @NotNull Component fmt(final @NotNull String text, final @NotNull String... placeholders) {
        final var tagResolvers = new TagResolver[placeholders.length];

        for (int i = 0; i < placeholders.length; i++) {
            tagResolvers[i] = Placeholder.unparsed(
                    Integer.toString(i),
                    placeholders[i]
            );
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }

    public static @NotNull Component makeMessageWithComponent(final @NotNull String color, final char symbol, final @NotNull String text, final @NotNull Component... components) {
        return MiniMessageHelper.fmtWithComponent(
                MiniMessageHelper.MESSAGE_TEMPLATE.formatted(color, symbol, color, text),
                components
        );
    }

    // Example: fmtWithComponent("My Component: <0>", event.getComponent());
    public static @NotNull Component fmtWithComponent(final @NotNull String text, final @NotNull Component... components) {
        final var tagResolvers = new TagResolver[components.length];

        for (int i = 0; i < components.length; i++) {
            tagResolvers[i] = Placeholder.component(
                    Integer.toString(i),
                    components[i]
            );
        }

        return MiniMessage.miniMessage().deserialize(text, tagResolvers);
    }
}
