package network.skulk.plugin.helpers;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class AttributeHelper {
    public static void addAttribute(final @NotNull ItemMeta itemMeta, final @NotNull Attribute attribute, final double level, final @NotNull EquipmentSlot equipmentSlot) {
        itemMeta.addAttributeModifier(
                attribute,
                new AttributeModifier(
                        UUID.randomUUID(),
                        attribute.name(),
                        level,
                        AttributeModifier.Operation.ADD_NUMBER,
                        equipmentSlot
                )
        );
    }
}
