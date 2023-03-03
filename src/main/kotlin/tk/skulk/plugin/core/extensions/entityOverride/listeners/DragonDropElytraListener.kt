package tk.skulk.plugin.core.extensions.entityOverride.listeners

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.attribute.Attribute
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.EntityType
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import tk.skulk.plugin.core.SNPlugin
import tk.skulk.plugin.core.booleanPersistentDataType
import tk.skulk.plugin.core.extensions.entityOverride.EntityOverrideExtension
import tk.skulk.plugin.framework.Listener
import tk.skulk.plugin.util.addAttribute
import tk.skulk.plugin.util.format
import tk.skulk.plugin.util.sendMessage
import kotlin.math.roundToInt


class DragonDropElytraListener(extension: EntityOverrideExtension) : Listener<SNPlugin, EntityOverrideExtension>(
    extension
) {
    private val opElytra = ItemStack(Material.ELYTRA).also {
        val meta = it.itemMeta.apply {

            displayName(
                format("<dark_purple>Dragon Master Wings</dark_purple>").decoration(
                    TextDecoration.ITALIC, false
                )
            )

            addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true)
            addEnchant(Enchantment.DURABILITY, 3, true)
            addEnchant(Enchantment.MENDING, 1, true)
            addEnchant(Enchantment.THORNS, 3, true)

            addAttribute(
                Attribute.GENERIC_ARMOR,
                8.0,
                EquipmentSlot.CHEST,
            )
            addAttribute(
                Attribute.GENERIC_ARMOR_TOUGHNESS,
                3.0,
                EquipmentSlot.CHEST,
            )
            addAttribute(
                Attribute.GENERIC_KNOCKBACK_RESISTANCE,
                1.0,
                EquipmentSlot.CHEST,
            )

            lore(
                listOf(
                    Component.empty(),
                    format("\"<dark_purple>Only true warriors can use these wings...</dark_purple>\"")
                )
            )
        }

        it.itemMeta = meta
    }

    private val hasGottenElytraKey = NamespacedKey(extension.plugin, "hasGottenElytra")

    private var lastDragonDamager: Player? = null

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entityType != EntityType.ENDER_DRAGON) {
            return
        }

        val damager = event.damager
        if (damager !is Player) {
            return
        }

        lastDragonDamager = damager
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entityType != EntityType.ENDER_DRAGON) {
            return
        }

        val player = lastDragonDamager ?: return


        if (player.persistentDataContainer.getOrDefault(
                hasGottenElytraKey, booleanPersistentDataType, false
            )) {
            return
        }

        val message = when {
            player.inventory.chestplate == null -> {
                player.inventory.chestplate = opElytra

                "You have been equipped with an OP elytra for beating the Ender dragon."
            }

            player.inventory.firstEmpty() != -1 -> {
                player.inventory.addItem(opElytra)

                "An OP elytra has been added to your inventory for beating the Ender dragon."
            }

            player.enderChest.firstEmpty() != -1 -> {
                player.enderChest.addItem(opElytra)

                "An OP elytra has been added to your ender chest for beating the Ender " + "dragon."
            }

            else -> {
                val l = player.location
                player.world.dropItem(l, opElytra)

                "An OP elytra has been dropped at x: ${l.x.roundToInt()}, y: ${l.y.roundToInt()}, z:" + " ${l.z.roundToInt()} since your inventory is full and you have beaten the " + "Ender dragon."
            }
        }

        player.sendMessage("light_purple", '!', message)

        player.persistentDataContainer.set(hasGottenElytraKey, booleanPersistentDataType, true)
    }
}
