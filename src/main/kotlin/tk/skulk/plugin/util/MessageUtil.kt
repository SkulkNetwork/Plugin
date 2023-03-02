package tk.skulk.plugin.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import tk.skulk.plugin.core.miniMessage

private const val template = "<b><gray>[ <color:%s>%s</color> ]</gray></b> <color:%s>%s</color>"

fun format(text: String, vararg placeholders: String) = miniMessage.deserialize(
    text, *placeholders.mapIndexed { i, s ->
    Placeholder.unparsed(i.toString(), s)
}.toTypedArray()
)

fun formatWithComponents(text: String, vararg placeholders: Component) = miniMessage.deserialize(
    text, *placeholders.mapIndexed { i, c ->
    Placeholder.component(i.toString(), c)
}.toTypedArray()
)

fun makeMessage(
    color: String,
    symbol: Char,
    text: String,
    vararg placeholders: String,
) = format(
    template.format(color, symbol, color, text), *placeholders
)

fun makeMessageWithComponents(
    color: String,
    symbol: Char,
    text: String,
    vararg placeholders: Component,
) = formatWithComponents(
    template.format(color, symbol, color, text), *placeholders
)

fun Audience.sendMessage(
    color: String,
    symbol: Char,
    message: String,
    vararg placeholders: String,
) {
    sendMessage(makeMessage(color, symbol, message, *placeholders))
}
