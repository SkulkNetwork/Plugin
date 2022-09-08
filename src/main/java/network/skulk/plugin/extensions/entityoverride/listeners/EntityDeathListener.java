package network.skulk.plugin.extensions.entityoverride.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

import static network.skulk.utils.MiniMessageFormat.fmt;
import static network.skulk.utils.MiniMessageFormat.sendMessage;
import static org.bukkit.attribute.AttributeModifier.Operation.ADD_SCALAR;

// Entity.getKiller() returns null when it's the ender dragon for some reason.
public final class EntityDeathListener extends BaseListener<EntityOverrideExtension> {
    private static final ItemStack elytra;

    static {
        elytra = new ItemStack(Material.ELYTRA, 1);

        final var meta = elytra.getItemMeta();
        meta.displayName(fmt("<dark_purple>Dragon Master Wings</dark_purple>").decoration(TextDecoration.ITALIC, false));

        meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
        meta.addEnchant(Enchantment.DURABILITY, 3, true);
        meta.addEnchant(Enchantment.MENDING, 1, true);
        meta.addEnchant(Enchantment.THORNS, 3, true);

        // FIXME: https://media.discordapp.net/attachments/555462289851940864/1017404069679206430/unknown.png?width=882&height=496
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier("generic.armor", 8, ADD_SCALAR));
        meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier("generic.armor_toughness", 3, ADD_SCALAR));
        meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier("generic.knockback_resistance", 1, ADD_SCALAR));

        final var lore = new ArrayList<Component>();
        // Newline.
        lore.add(Component.empty());
        lore.add(fmt("<dark_purple>Only true warriors can use these wings...</dark_purple>"));
        meta.lore(lore);

        elytra.setItemMeta(meta);
    }

    private @Nullable Player lastDragonDamager = null;

    public EntityDeathListener(final EntityOverrideExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(final EntityDamageByEntityEvent event) {
        final var damager = event.getDamager();
        System.out.println(damager.getType());
        System.out.println(event.getEntityType());
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
    }
}
