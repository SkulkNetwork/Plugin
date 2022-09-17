package network.skulk.plugin.extensions.entityoverride.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.utils.AttributeHelper;
import network.skulk.utils.Singletons;
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
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static network.skulk.utils.MiniMessageHelper.fmt;
import static network.skulk.utils.MiniMessageHelper.sendMessage;

// Entity.getKiller() returns null when it's the ender dragon for some reason.
public final class DragonDropElytraListener extends BaseListener<EntityOverrideExtension> {
    private static final ItemStack elytra;
    private final NamespacedKey hasGottenElytraKey = new NamespacedKey(this.getExtension().getPlugin(), "hasGottenElytra");

    static {
        elytra = new ItemStack(Material.ELYTRA, 1);

        final var meta = elytra.getItemMeta();
        meta.displayName(fmt("<dark_purple>Dragon Master Wings</dark_purple>").decoration(TextDecoration.ITALIC, false));

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(Enchantment.THORNS, 3, true);

        AttributeHelper.addAttribute(meta, Attribute.GENERIC_ARMOR, 8, EquipmentSlot.CHEST);
        AttributeHelper.addAttribute(meta, Attribute.GENERIC_ARMOR_TOUGHNESS, 3, EquipmentSlot.CHEST);
        AttributeHelper.addAttribute(meta, Attribute.GENERIC_KNOCKBACK_RESISTANCE, 1, EquipmentSlot.CHEST);

        final var lore = new ArrayList<Component>();
        // Newline.
        lore.add(Component.empty());
        lore.add(fmt("<dark_purple>Only true warriors can use these wings...</dark_purple>"));
        meta.lore(lore);

        elytra.setItemMeta(meta);
    }

    private @Nullable Player lastDragonDamager = null;

    public DragonDropElytraListener(final EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final var damager = event.getDamager();

        if (damager instanceof Player player) {
            this.lastDragonDamager = player;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDeath(final EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDER_DRAGON) {
            return;
        }

        final var player = this.lastDragonDamager;

        if (player == null) {
            event.getDrops().add(elytra);
            return;
        }

        final var playerPdc = player.getPersistentDataContainer();
        final var booleanPdt = Singletons.getBooleanPdt();

        if (playerPdc.getOrDefault(this.hasGottenElytraKey, booleanPdt, false)) {
            return;
        }

        final var playerInventory = player.getInventory();
        final var playerEnderChest = player.getEnderChest();

        if (playerInventory.getChestplate() == null) {
            playerInventory.setChestplate(elytra);
            sendMessage(player, "light_purple", '!', "You have been equipped with an OP elytra for beating the Ender dragon.");

        } else if (playerInventory.firstEmpty() != -1) {
            playerInventory.addItem(elytra);
            sendMessage(player, "light_purple", '!', "An OP elytra has been added to your inventory for beating the Ender dragon.");

        } else if (playerEnderChest.firstEmpty() != -1) {
            playerEnderChest.addItem(elytra);
            sendMessage(player, "light_purple", '!', "An OP elytra has been added to your ender chest for beating the Ender dragon.");

        } else {
            final var l = player.getLocation();
            player.getWorld().dropItem(l, elytra);
            sendMessage(player, "light_purple", '!', "An OP elytra has been dropped at x: %.0f, y: %.0f, z: %.0f since your inventory is full and you have beat the Ender dragon.".formatted(l.getX(), l.getY(), l.getZ()));
        }

        playerPdc.set(this.hasGottenElytraKey, booleanPdt, true);
    }
}
