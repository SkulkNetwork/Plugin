package tk.skulk.plugin.util

import net.kyori.adventure.text.Component
import tk.skulk.plugin.core.ptSerializer

fun Component.asString() = ptSerializer.serialize(this)
