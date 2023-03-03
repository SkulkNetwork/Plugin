package tk.skulk.plugin.core.extensions.updateChecker

import com.google.gson.annotations.SerializedName
import okhttp3.Request
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.extensions.updateChecker.commands.UpdateCommand
import tk.skulk.plugin.core.extensions.updateChecker.listeners.OpJoinListener
import tk.skulk.plugin.core.gson
import tk.skulk.plugin.core.http
import tk.skulk.plugin.framework.Extension

val request = Request
    .Builder()
    .url("https://api.github.com/repos/Skulk-Network/Plugin/releases/latest")
    .get()
    .build()

class ReleaseTagNameResponse(
    @SerializedName("tag_name")
    val tagName: String,
)

class UpdateCheckerExtension(plugin: SNPlugin) : Extension<SNPlugin>(plugin) {
    override val commands = listOf {
        UpdateCommand(this)
    }

    override val listeners = listOf {
        OpJoinListener(this)
    }

    val latestVersion: String
        get() = http.newCall(request).execute().use { response ->
            gson.fromJson(
                response.body!!.string(), ReleaseTagNameResponse::class.java
            )!!.tagName
        }
}
