package tk.skulk.plugin.util

import org.bukkit.Bukkit
import tk.skulk.plugin.framework.Plugin

fun runAsync(block: () -> Unit) {
    Bukkit.getScheduler().runTaskAsynchronously(Plugin.instance, block)
}

fun runSync(block: () -> Unit) {
    Bukkit.getScheduler().runTask(Plugin.instance, block)
}
