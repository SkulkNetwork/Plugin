package tk.skulk.plugin.framework

import com.google.errorprone.annotations.DoNotCall
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList

/**
 * The class that represents a listener collection.
 *
 * To add a listener to the class, you need to define a method that
 * is annotated with [org.bukkit.event.EventHandler] which has a
 * single parameter that is a subclass of [org.bukkit.event.Event].
 *
 * @constructor The method that gets called when the listener is getting unloaded.
 */
abstract class Listener<P : Plugin, E : Extension<P>>(
    /** The [Extension] that owns the listener. **/
    protected val extension: E,
) : org.bukkit.event.Listener {
    /**
     * The method that gets called every time the listener is unloaded.
     *
     * When the listener is unloaded, the listener will be unregistered
     * and cleaned up by the garbage collector. One listener instance
     * does not live longer than one load. Every time /reload is
     * executed, the listener class will be initialized again.
     */
    protected open fun disable() {}

    @DoNotCall
    fun unload() {
        try {
            disable()
        }
        catch (error: Throwable) {
            extension.plugin.logger.severe("An error occurred while unloading listener '${this::class.simpleName}':\n${error.stackTraceToString()}")
        }

        HandlerList.unregisterAll(this)
    }

    init {
        @Suppress("LeakingThis") Bukkit.getPluginManager().registerEvents(this, extension.plugin)
    }
}
