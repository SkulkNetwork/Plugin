package tk.skulk.plugin.util

import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.meta.ItemMeta
import java.util.UUID

fun addAttribute(
    itemMeta: ItemMeta,
    attribute: Attribute,
    level: Double,
    equipmentSlot: EquipmentSlot,
) {
    itemMeta.addAttributeModifier(
        attribute, AttributeModifier(
        UUID.randomUUID(),
        attribute.name,
        level,
        AttributeModifier.Operation.ADD_NUMBER,
        equipmentSlot
    )
    )
}
