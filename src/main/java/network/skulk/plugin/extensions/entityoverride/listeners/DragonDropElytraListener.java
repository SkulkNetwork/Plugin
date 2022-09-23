package network.skulk.plugin.extensions.entityoverride.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import network.skulk.helpers.AttributeHelper;
import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.singletons.Singletons;
import network.skulk.wrapper.BaseListener;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static network.skulk.helpers.MiniMessageHelper.fmt;
import static network.skulk.helpers.MiniMessageHelper.sendMessage;

// Entity.getKiller() returns null when it's the ender dragon for some reason.
public final class DragonDropElytraListener extends BaseListener<EntityOverrideExtension> {
    private static final ItemStack OP_ELYTRA = new ItemStack(Material.ELYTRA, 1);

    static {

        final var elytraItemMeta = DragonDropElytraListener.OP_ELYTRA.getItemMeta();

        elytraItemMeta.displayName(fmt("<dark_purple>Dragon Master Wings</dark_purple>").decoration(TextDecoration.ITALIC, false));

        elytraItemMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        elytraItemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        elytraItemMeta.addEnchant(Enchantment.MENDING, 1, true);
        elytraItemMeta.addEnchant(Enchantment.THORNS, 3, true);

        AttributeHelper.addAttribute(elytraItemMeta, Attribute.GENERIC_ARMOR, 8, EquipmentSlot.CHEST);
        AttributeHelper.addAttribute(elytraItemMeta, Attribute.GENERIC_ARMOR_TOUGHNESS, 3, EquipmentSlot.CHEST);
        AttributeHelper.addAttribute(elytraItemMeta, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1, EquipmentSlot.CHEST);

        final var elytraLore = new ArrayList<Component>();
        // Newline.
        elytraLore.add(Component.empty());
        elytraLore.add(fmt("<dark_purple>Only true warriors can use these wings...</dark_purple>"));

        elytraItemMeta.lore(elytraLore);

        DragonDropElytraListener.OP_ELYTRA.setItemMeta(elytraItemMeta);
    }

    private final NamespacedKey hasGottenElytraKey = new NamespacedKey(this.getExtension().getPlugin(), "hasGottenElytra");
    private @Nullable Player lastDragonDamager = null;

    public DragonDropElytraListener(final @NotNull EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(final @NotNull EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            this.lastDragonDamager = player;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDeath(final @NotNull EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDER_DRAGON || this.lastDragonDamager == null) {
            return;
        }

        final var player = this.lastDragonDamager;
        final var playerPersistentDataContainer = player.getPersistentDataContainer();
        final var booleanPersistentDataType = Singletons.getBooleanPersistentDataType();

        if (playerPersistentDataContainer.getOrDefault(this.hasGottenElytraKey, booleanPersistentDataType, false)) {
            return;
        }

        final var playerInventory = player.getInventory();

        if (playerInventory.getChestplate() == null) {
            playerInventory.setChestplate(OP_ELYTRA);
            sendMessage(player, "light_purple", '!', "You have been equipped with an OP elytra for beating the Ender dragon.");
        }
        else if (playerInventory.firstEmpty() != -1) {
            playerInventory.addItem(OP_ELYTRA);
            sendMessage(player, "light_purple", '!', "An OP elytra has been added to your inventory for beating the Ender dragon.");
        }
        else if (player.getEnderChest().firstEmpty() != -1) {
            player.getEnderChest().addItem(OP_ELYTRA);
            sendMessage(player, "light_purple", '!', "An OP elytra has been added to your ender chest for beating the Ender dragon.");
        }
        else {
            final var playerLocation = player.getLocation();
            player.getWorld().dropItem(playerLocation, OP_ELYTRA);
            sendMessage(
                    player,
                    "light_purple", '!', "An OP elytra has been dropped at x: %.0f, y: %.0f, z: %.0f since your inventory is full and you have beat the Ender dragon."
                            .formatted(playerLocation.getX(), playerLocation.getY(), playerLocation.getZ())
            );
        }

        playerPersistentDataContainer.set(this.hasGottenElytraKey, booleanPersistentDataType, true);
    }
}
