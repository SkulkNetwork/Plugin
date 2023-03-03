package tk.skulk.plugin.core

import com.google.gson.Gson
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import okhttp3.OkHttpClient
import redis.clients.jedis.JedisPooled
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.core.extensions.foodBehaviour.FoodBehaviourExtension
import tk.skulk.plugin.core.extensions.silenceMobs.SilenceMobsExtension
import tk.skulk.plugin.framework.Plugin

val gson = Gson()
val http = OkHttpClient()
val miniMessage = MiniMessage.miniMessage()
val ptSerializer = PlainTextComponentSerializer.plainText()
val redis = JedisPooled("localhost", 6379)

class SNPlugin : Plugin() {
    override val extensions = listOf(
        { EntityOverrideExtension(this) },
        { FoodBehaviourExtension(this) },
        { SilenceMobsExtension(this) },
    )
}
