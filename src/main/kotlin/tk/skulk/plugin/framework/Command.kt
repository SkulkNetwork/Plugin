package tk.skulk.plugin.framework

import org.bukkit.command.CommandExecutor

/**
 * A listener is a class runs a command.
 *
 * @param extension The extension to register this to.
 */
abstract class Command<P : Plugin, E : Extension<P>>(
    protected val extension: E
) : CommandExecutor {
    /** The name of the command. **/
    abstract val name: String
    /** The description of the command. **/
    abstract val description: String
    /** The usage of the command. **/
    abstract val usage: String

    /**
     * Initializes the command.
     *
     * This method is called every time when the command is loaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun initialize()

    /**
     * Uninitializes the command.
     *
     * This method is called every time when the command is unloaded.
     *
     * @access Private, only called by the plugin.
     */
    abstract fun uninitialize()
}
