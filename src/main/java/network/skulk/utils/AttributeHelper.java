package network.skulk.utils;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.UUID;

public abstract class AttributeHelper {
    public static void addAttribute(final ItemMeta meta, final Attribute attribute, final double level, final EquipmentSlot slot) {
        meta.addAttributeModifier(attribute, new AttributeModifier(UUID.randomUUID(), attribute.name(), level, AttributeModifier.Operation.ADD_NUMBER, slot));
    }
}
