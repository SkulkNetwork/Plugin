package tk.skulk.plugin.framework

import com.google.errorprone.annotations.DoNotCall

/**
 * The class that is used to extend the functionality of a [Plugin].
 *
 * # Example
 *
 * ```kt
 * class MyExtension(plugin: MyPlugin) : Extension(plugin) {
 *     override val commands = listOf(
 *         { MyCommand(this) },
 *         { MyOtherCommand(this) },
 *     )
 *
 *     override val listeners = listOf(
 *         { MyListener(this) },
 *         { MyOtherListener(this) },
 *     )
 *
 *     override fun disable() {
 *         // Do stuff.
 *     }
 * }
 * ```
 *
 * @constructor The method that gets called when the extension is getting unloaded.
 */
abstract class Extension<P : Plugin>(
    /** The [Plugin] that owns the extension. **/
    val plugin: P,
) {
    /**
     * The commands owned by the extension.
     *
     * Commands are loaded in the order they are defined in this list.
     *
     * # Example
     *
     * ```kt
     * override val commands = listOf(
     *     { MyCommand(this) },
     *     { MyOtherCommand(this) },
     * )
     * ```
     */
    protected abstract val commands: List<() -> Command<P, *>>

    /**
     * The listeners owned by the extension.
     *
     * Listeners are loaded in the order they are defined in this list.
     *
     * # Example
     * ```kt
     * override val listeners = listOf(
     *     { MyListener(this) },
     *     { MyOtherListener(this) },
     * )
     * ```
     */
    protected abstract val listeners: List<() -> Listener<P, *>>

    /**
     * The method that gets called when the extension is getting unloaded. Must be overriden.
     *
     * When the extension is unloaded, the extension will be unregistered
     * and cleaned up by the garbage collector. One extension instance
     * does not live longer than one load. Every time /reload is
     * executed, the extension class will be initialized again.
     */
    protected abstract fun disable()

    @DoNotCall
    fun unload() {
        loadedCommands.forEach { it.unload() }
        loadedCommands.clear()

        loadedListeners.forEach { it.unload() }
        loadedListeners.clear()

        try {
            disable()
        }
        catch (error: Throwable) {
            plugin.logger.severe("An error occurred while unloading extension '${this::class.simpleName}':\n${error.stackTraceToString()}")
        }
    }

    private val loadedCommands = mutableListOf<Command<P, *>>()
    private val loadedListeners = mutableListOf<Listener<P, *>>()

    init {
        @Suppress("LeakingThis") commands.forEach {
            try {
                loadedCommands += it()
            }
            catch (_: Throwable) { // Registration failure.
            }
        }
        @Suppress("LeakingThis") listeners.forEach {
            try {
                loadedListeners += it()
            }
            catch (_: Throwable) { // Registration failure.
            }
        }
    }
}
