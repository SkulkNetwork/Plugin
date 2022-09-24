package network.skulk.plugin.extensions.silencemobs.listeners;

import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import network.skulk.plugin.extensions.silencemobs.SilenceMobsExtension;
import network.skulk.wrapper.BaseListener;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

import static network.skulk.helpers.MiniMessageHelper.fmt;
import static network.skulk.helpers.MiniMessageHelper.sendMessage;

public final class PlayerInteractEntityListener extends BaseListener<SilenceMobsExtension> {
    private static final HashSet<String> silencingNames = new HashSet<>();

    static {
        PlayerInteractEntityListener.silencingNames.add("[silenced]");
        PlayerInteractEntityListener.silencingNames.add("[silenceme]");
        PlayerInteractEntityListener.silencingNames.add("[silence me]");
        PlayerInteractEntityListener.silencingNames.add("[stfu]");
        PlayerInteractEntityListener.silencingNames.add("[shutup]");
        PlayerInteractEntityListener.silencingNames.add("[shut up]");
    }

    public PlayerInteractEntityListener(final SilenceMobsExtension extension) {
        super(extension);
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onPlayerInteractEntity(final PlayerInteractEntityEvent event) {
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

        if (!PlayerInteractEntityListener.silencingNames.contains(PlainTextComponentSerializer.plainText().serialize(nameTags.displayName()).toLowerCase())) {
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

        sendMessage(player, "green", '✓', "The entity '<0>' has been silenced.", entity.getType().toString().toLowerCase());

        event.setCancelled(true);
    }
}
