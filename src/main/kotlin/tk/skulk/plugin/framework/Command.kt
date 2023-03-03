package tk.skulk.plugin.framework

import com.google.errorprone.annotations.DoNotCall
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

/**
 * The class that represents a command.
 *
 * @constructor The method that gets called when the command is getting unloaded.
 */
abstract class Command<P : Plugin, E : Extension<P>>(
    /** The [Extension] that owns the command. **/
    protected val extension: E,
) : CommandExecutor {
    /** The name of the command. Must be overriden. **/
    protected abstract val name: String

    /** The description of the command. Must be overriden. **/
    protected abstract val description: String

    /** The usage of the command. Must be overriden. **/
    protected abstract val usage: String

    /**
     * The method that gets called every time the command is executed.
     *
     * @param sender The [CommandSender] that executed the command.
     * @param args The arguments that were passed to the command.
     */
    protected abstract fun execute(sender: CommandSender, args: Array<String>): Boolean

    /**
     * The method that gets called every time the command is unloaded.
     *
     * When the command is unloaded, the command will be unregistered
     * and cleaned up by the garbage collector. One command instance
     * does not live longer than one load. Every time /reload is
     * executed, the command class will be initialized again.
     */
    protected open fun disable() {}

    @DoNotCall
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>,
    ) = execute(sender, args)

    @DoNotCall
    fun unload() {
        try {
            disable()
        }
        catch (error: Throwable) {
            extension.plugin.logger.severe("An error occurred while unloading command '$name':\n${error.stackTraceToString()}")
        }

        extension.plugin.getCommand(name)?.let {
            it.setExecutor(null)
            it.description = ""
            it.usage = ""
        }
    }

    init {
        @Suppress("LeakingThis") extension.plugin.getCommand(name)?.let {
            it.setExecutor(this)
            it.description = description
            it.usage = usage

            it
        } ?: run {
            extension.plugin.logger.severe("Failed to load command '$name': Command does not exist.")
            throw Throwable()
        }
    }
}
