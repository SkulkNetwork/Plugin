package network.skulk.plugin.extensions.entityoverride.listeners;

import net.kyori.adventure.text.Component;
import network.skulk.plugin.extensions.entityoverride.EntityOverrideExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.Material;
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

// Entity.getKiller() returns null when it's the ender dragon for some reason.
public final class EntityDeathListener extends BaseListener<EntityOverrideExtension> {
    private static final ItemStack elytra;

    static {
        elytra = new ItemStack(Material.ELYTRA, 1);

        final var elytraEnchantments = elytra.getEnchantments();
        elytraEnchantments.put(Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        elytraEnchantments.put(Enchantment.DURABILITY, 3);
        elytraEnchantments.put(Enchantment.MENDING, 1);
        elytraEnchantments.put(Enchantment.THORNS, 3);

        final var elytraMeta = elytra.getItemMeta();
        elytraMeta.displayName(fmt("<dark_purple>Dragon Master Wings</dark_purple>"));

        final var lore = new ArrayList<Component>();
        lore.add(fmt("<light_purple>Only true warriors can use these wings...</light_purple>"));
        elytraMeta.lore(lore);
    }

    private @Nullable Player lastDragonDamager = null;

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
