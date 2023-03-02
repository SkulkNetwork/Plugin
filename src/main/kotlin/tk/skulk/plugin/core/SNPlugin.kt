package tk.skulk.plugin.core

import com.google.gson.Gson
import net.kyori.adventure.text.minimessage.MiniMessage
import okhttp3.OkHttpClient
import tk.skulk.plugin.framework.Extension
import tk.skulk.plugin.framework.Plugin

val miniMessage = MiniMessage.miniMessage()
val gson = Gson()
val http = OkHttpClient()

class SNPlugin : Plugin() {
    override val extensions = listOf<() -> Extension<SNPlugin>>(

    )
}
