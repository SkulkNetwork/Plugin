package tk.skulk.plugin.framework

import org.bukkit.plugin.java.JavaPlugin

/**
 * The base class for all plugins.
 *
 * # Example
 *
 * ```kt
 * class MyPlugin : Plugin() {
 *     override val extensions = listOf(
 *         { MyExtension(this) },
 *         { MyOtherExtension(this) },
 *     )
 * }
 * ```
 */
abstract class Plugin : JavaPlugin() {
    /**
     * The extensions owned by the plugin.
     *
     * Extensions are loaded in the order they are defined in this list.
     *
     * # Example
     * ```kt
     * override val extensions = listOf(
     *     { MyExtension(this) },
     *     { MyOtherExtension(this) },
     * )
     * ```
     */
    protected abstract val extensions: List<() -> Extension<*>>

    /** The (currently) loaded extensions. **/
    private val loadedExtensions = mutableListOf<Extension<*>>()

    /** Loads all extensions. **/
    override fun onEnable() {
        extensions.forEach {
            try {
                loadedExtensions += it() // Registration failure.
            }
            catch (_: Throwable) {
            }
        }
    }

    /** Unloads all loaded extensions. **/
    override fun onDisable() {
        server.scheduler.cancelTasks(this)

        loadedExtensions.forEach {
            it.unload()
        }
    }
}
