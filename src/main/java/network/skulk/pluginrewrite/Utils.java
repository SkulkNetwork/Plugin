package network.skulk.pluginrewrite;

public abstract class Utils {
    public static String fmt(final String color, final String symbol, final String text) {
        return "<bold><gray>[ <color:%s>%s</color> ]</gray></bold> <color:%s>%s</color>".formatted(color, symbol, color, text);
    }
}
