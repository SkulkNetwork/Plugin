package tk.skulk.plugin.core

import com.google.gson.Gson
import net.kyori.adventure.text.minimessage.MiniMessage
import okhttp3.OkHttpClient
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.framework.Extension
import tk.skulk.plugin.framework.Plugin
import tk.skulk.plugin.pdts.BooleanPersistentDataType

val booleanPersistentDataType = BooleanPersistentDataType()
val gson = Gson()
val http = OkHttpClient()
val miniMessage = MiniMessage.miniMessage()


class SNPlugin : Plugin() {
    override val extensions = listOf<() -> Extension<SNPlugin>>(
        { EntityOverrideExtension(this) },
    )
}
