package tk.skulk.plugin.core

import com.google.gson.Gson
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import okhttp3.OkHttpClient
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.core.extensions.foodBehaviour.FoodBehaviourExtension
import tk.skulk.plugin.core.extensions.silenceMobs.SilenceMobsExtension
import tk.skulk.plugin.framework.Plugin
import tk.skulk.plugin.pdts.BooleanPersistentDataType

val booleanPersistentDataType = BooleanPersistentDataType()
val gson = Gson()
val http = OkHttpClient()
val miniMessage = MiniMessage.miniMessage()
val ptSerializer = PlainTextComponentSerializer.plainText()

class SNPlugin : Plugin() {
    override val extensions = listOf(
        { EntityOverrideExtension(this) },
        { FoodBehaviourExtension(this) },
        { SilenceMobsExtension(this) },
    )
}
