package tk.skulk.plugin.framework

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

// FIXME: All things do not get unloaded when something goes wrong.
/**
 * The base class for all plugins.
 *
 * ## Code Example
 *
 * ```kt
 * class MyPlugin : Plugin() {
 *    override fun extensions() =
 *        listOf(
 *            MyExtension(this)
 *        )
 * }
 * ```
 */
abstract class Plugin : JavaPlugin() {
    /**
     * Returns a list of extensions to load.
     *
     * This method is called every time when the extension is loaded.
     *
     * @access Private, only called by the plugin.
     */
    protected abstract fun extensions(): List<Extension<*>>

    // The currently loaded extensions.
    private val loadedExtensions = mutableListOf<Extension<*>>()

    /** Loads the given command. Returns true if the command was loaded successfully. **/
    private fun load(commandExecutor: Command<*, *>): Boolean {
        try {
            commandExecutor.initialize()
        } catch (error: Throwable) {
            logger.severe(
                "Failed to initialize ${commandExecutor::class.simpleName}:\n"
                    + error.stackTraceToString().prependIndent("    ")
            )
            return false
        }

        val command = Bukkit.getPluginCommand(commandExecutor.name) ?: run {
            logger.severe("Failed to load command ${commandExecutor.name} (Supplied from ${commandExecutor::class.simpleName}): Command not found.")
            return@load false
        }
        command.setExecutor(commandExecutor)

        command.description = commandExecutor.description
        command.usage = commandExecutor.usage

        return true
    }

    /** Loads the given listener. Returns true if the listener was loaded successfully. **/
    private fun load(listener: Listener<*, *>): Boolean {
        try {
            listener.initialize()
        } catch (error: Throwable) {
            logger.severe(
                "Failed to initialize listener ${listener::class.simpleName}:\n"
                    + error.stackTraceToString().prependIndent("    ")
            )
            return false
        }

        Bukkit.getPluginManager().registerEvents(listener, this)
        return true
    }

    /** Loads the given extension. Returns true if the extension was loaded successfully. **/
    private fun load(extension: Extension<*>): Boolean {
        try {
            extension.initialize()
        } catch (error: Throwable) {
            logger.severe(
                "Failed to initialize extension ${extension::class.simpleName}:\n"
                    + error.stackTraceToString().prependIndent("    ")
            )
            return false
        }

        extension.commands.forEach {
            load(it) || return@load false
        }
        extension.listeners.forEach {
            load(it) || return@load false
        }

        return true
    }

    /** Unloads the given listener. **/
    private fun unload(listener: Listener<*, *>) {
        // May throw.
        listener.uninitialize()
    }

    /** Unloads the given command. **/
    private fun unload(commandExecutor: Command<*, *>) {
        // May throw.
        commandExecutor.uninitialize()
    }

    /** Unloads the given extension. **/
    private fun unload(extension: Extension<*>) {
        extension.listeners.reversed().forEach { unload(it) }
        extension.commands.reversed().forEach { unload(it) }

        extension.uninitialize()
    }

    /**
     * Loads all extensions.
     *
     * @access Private, only called by Bukkit.
     */
    override fun onEnable() {
        logger.info("Loading extensions...")

        extensions().forEach {
            load(it) && loadedExtensions.add(it)
        }

        logger.info("Extensions loaded.")
    }

    /**
     * Unloads all loaded extensions.
     *
     * @access Private, only called by Bukkit.
     */
    override fun onDisable() {
        logger.info("Unloading extensions...")

        Bukkit.getScheduler().cancelTasks(this)

        try {
            loadedExtensions.forEach { unload(it) }
        } catch (error: Throwable) {
            logger.severe(
                "Failed to unload extensions:\n"
                    + error.stackTraceToString().prependIndent("    ")
            )
        }

        logger.info("Extensions unloaded.")
    }
}
