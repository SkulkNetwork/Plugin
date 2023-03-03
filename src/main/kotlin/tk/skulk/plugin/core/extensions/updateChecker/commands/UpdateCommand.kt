package tk.skulk.plugin.core.extensions.updateChecker.commands

import org.bukkit.command.CommandSender
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.updateChecker.UpdateCheckerExtension
import tk.skulk.plugin.framework.Command

class UpdateCommand(extension: UpdateCheckerExtension) : Command<SNPlugin, UpdateCheckerExtension>(
    extension
) {
    override val name = "update"
    override val description = "Update the plugin."
    override val usage = "/update"

    override fun onCommand(
        sender: CommandSender,
        command: org.bukkit.command.Command,
        label: String,
        args: Array<String>,
    ): Boolean {
        TODO("Not yet implemented")
    }
}
