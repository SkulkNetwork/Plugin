package tk.skulk.plugin.framework

/**
 * A listener is a class that listens to events.
 *
 * @param extension The extension to register this to.
 */
abstract class Listener<P : Plugin, E : Extension<P>>(
    protected val extension: E
) : org.bukkit.event.Listener {

    /**
     * Initializes the listener.
     *
     * This method is called every time when the listener is loaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun initialize()

    /**
     * Uninitializes the listener.
     *
     * This method is called every time when the listener is unloaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun uninitialize()
}
