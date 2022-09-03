package network.skulk.utils;

public abstract class MessageFormat {
    public static String fmt(final String color, final char symbol, final String text) {
        return "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);
    }
}
