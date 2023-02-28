package tk.skulk.plugin.framework

/**
 * An extension is a class that extends the functionality of a plugin.
 *
 * @param plugin The plugin to extend.
 */
abstract class Extension<P : Plugin>(val plugin: P) {
    /** The commands of the extension. **/
    abstract val commands: List<Command<P, *>>
    /** The listeners of the extension. **/
    abstract val listeners: List<Listener<P, *>>

    /**
     * Initializes the extension.
     *
     * This method is called every time when the extension is loaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun initialize()

    /**
     * Uninitializes the extension.
     *
     * This method is called every time when the extension is unloaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun uninitialize()
}
