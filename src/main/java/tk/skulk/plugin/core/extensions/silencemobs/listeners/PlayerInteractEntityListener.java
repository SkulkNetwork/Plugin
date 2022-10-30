package tk.skulk.plugin.core.extensions.silencemobs.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tk.skulk.plugin.core.extensions.silencemobs.SilenceMobsExtension;
import tk.skulk.plugin.wrapper.BaseListener;

import static tk.skulk.plugin.helpers.MiniMessageHelper.fmt;
import static tk.skulk.plugin.helpers.MiniMessageHelper.sendMessage;

public final class PlayerInteractEntityListener extends BaseListener<SilenceMobsExtension> {
    public PlayerInteractEntityListener(final @NotNull SilenceMobsExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(final @NotNull PlayerInteractEntityEvent event) {
        final var player = event.getPlayer();
        final var playerInventory = player.getInventory();
        final var mainHandItem = playerInventory.getItemInMainHand();
        final var offHandItem = playerInventory.getItemInOffHand();

        final ItemStack nameTags;

        if (mainHandItem.getType() == Material.NAME_TAG) {
            nameTags = mainHandItem;
        }
        else if (offHandItem.getType() == Material.NAME_TAG) {
            nameTags = offHandItem;
        }
        else {
            return;
        }

        if (!PlainTextComponentSerializer.plainText().serialize(nameTags.displayName()).equalsIgnoreCase(
            "[silenced]")) {
            return;
        }

        final var entity = event.getRightClicked();

        if (entity.getType() == EntityType.UNKNOWN) {
            return;
        }

        entity.setSilent(true);
        entity.customName(fmt("<i><gray>Silenced</gray></i>"));

        if (player.getGameMode() != GameMode.CREATIVE) {
            nameTags.setAmount(nameTags.getAmount() - 1);
        }

        sendMessage(
            player,
            "green",
            '✓',
            "The entity '<0>' has been silenced.",
            entity.getType().toString().toLowerCase()
        );

        event.setCancelled(true);
    }
}
