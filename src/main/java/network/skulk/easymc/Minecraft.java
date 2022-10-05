package network.skulk.easymc;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SuppressWarnings("unused")
public final class Minecraft {
    private static final @NotNull Server server = Bukkit.getServer();

    private Minecraft() {
    }

    // TODO: Custom class, or remove this
    @Contract(pure = true)
    public static @NotNull Server getServer() {
        return Minecraft.server;
    }

    @Contract(pure = true)
    public static @NotNull String getName() {
        return Minecraft.server.getName();
    }

    // Versions

    @Contract(pure = true)
    public static @NotNull String getVersionMessage() {
        return Bukkit.getVersionMessage();
    }

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        return Minecraft.server.getVersion();
    }

    @Contract(pure = true)
    public static @NotNull String getBukkitVersion() {
        return Minecraft.server.getBukkitVersion();
    }

    @Contract(pure = true)
    public static @NotNull String getMinecraftVersion() {
        return Minecraft.server.getMinecraftVersion();
    }

    // Players

    @Contract(pure = true)
    public static @NotNull Collection<? extends Player> getOnlinePlayers() {
        return Minecraft.server.getOnlinePlayers();
    }

    @Contract(pure = true)
    public static int getPlayerCap() {
        return Minecraft.server.getMaxPlayers();
    }

    public static void setPlayerCap(final int cap) {
        Minecraft.server.setMaxPlayers(cap);
    }

    @Contract(pure = true)
    public static @Nullable Player getPlayer(final @NotNull String name) {
        return Minecraft.server.getPlayerExact(name);
    }

    @Contract(pure = true)
    public static @Nullable Player getPlayer(final @NotNull UUID uuid) {
        return Minecraft.server.getPlayer(uuid);
    }

    @Contract(pure = true)
    public static @Nullable UUID getPlayerUUID(final @NotNull String name) {
        return Minecraft.server.getPlayerUniqueId(name);
    }

    @Contract(pure = true)
    public static @Nullable Player getPlayerLoose(final @NotNull String name) {
        return Minecraft.server.getPlayer(name);
    }

    @Contract(pure = true)
    public static @NotNull List<? extends Player> getPlayersThatMatchString(final @NotNull String name) {
        return Minecraft.server.matchPlayer(name);
    }

    // Distances

    @Contract(pure = true)
    public static int getViewDistance() {
        return Minecraft.server.getViewDistance();
    }

    @Contract(pure = true)
    public static int getSimulationDistance() {
        return Minecraft.server.getSimulationDistance();
    }

    // Worlds

    @Contract(pure = true)
    public static @NotNull List<World> getWorlds() {
        return Minecraft.server.getWorlds();
    }

    @Contract(pure = true)
    public static @NotNull String getDefaultWorldType() {
        return Minecraft.server.getWorldType();
    }

    @Contract(pure = true)
    public static int getWorldSizeCap() {
        return Minecraft.server.getMaxWorldSize();
    }

    @Contract(pure = true)
    public static boolean getIsGeneratingStructures() {
        return Minecraft.server.getGenerateStructures();
    }

    @Contract(pure = true)
    public static boolean getIsAllowingNether() {
        return Minecraft.server.getAllowNether();
    }

    @Contract(pure = true)
    public static boolean getIsAllowingEnd() {
        return Minecraft.server.getAllowEnd();
    }

    // Resource Pack

    @Contract(pure = true)
    public static @NotNull String getResourcePackUri() {
        return Minecraft.server.getResourcePack();
    }

    @Contract(pure = true)
    public static @NotNull String getResourcePackHash() {
        return Minecraft.server.getResourcePackHash();
    }

    @Contract(pure = true)
    public static @NotNull String getResourcePackPrompt() {
        return Minecraft.server.getResourcePackPrompt();
    }

    @Contract(pure = true)
    public static boolean getIsResourcePackRequired() {
        return Minecraft.server.isResourcePackRequired();
    }

    // Whitelist

    @Contract(pure = true)
    public static boolean getIsWhitelistEnabled() {
        return Minecraft.server.isWhitelistEnforced();
    }

    public static void setWhitelistEnabled(final boolean enabled) {
        Minecraft.server.setWhitelist(enabled);
    }

    @Contract(pure = true)
    public static @NotNull Set<OfflinePlayer> getWhitelistedPlayers() {
        return Minecraft.server.getWhitelistedPlayers();
    }

    @Contract(pure = true)
    public static void reloadWhitelist() {
        Minecraft.server.reloadWhitelist();
    }

    // Broadcasting

    // Strings
    public static void broadcastMessage(final @NotNull String message) {
        Minecraft.server.broadcast(Component.text(message));
    }

    public static void broadcastMessage(final @NotNull String message, final @NotNull String permission) {
        Minecraft.server.broadcast(Component.text(message), permission);
    }

    // Components
    public static void broadcastMessage(final @NotNull Component message) {
        Minecraft.server.broadcast(message);
    }

    public static void broadcastMessage(final @NotNull Component message, final @NotNull String permission) {
        Minecraft.server.broadcast(message, permission);
    }

    // ComponentLikes
    public static void broadcastMessage(final @NotNull ComponentLike message) {
        Minecraft.server.broadcast(message.asComponent());
    }

    public static void broadcastMessage(final @NotNull ComponentLike message, final @NotNull String permission) {
        Minecraft.server.broadcast(message.asComponent(), permission);
    }

    // Folders

    @Contract(pure = true)
    public static @NotNull File getPluginsFolder() {
        return Minecraft.server.getPluginsFolder();
    }

    @Contract(pure = true)
    public static @NotNull File getPluginsUpdateFolder() {
        return new File(Minecraft.server.getPluginsFolder(), Minecraft.server.getUpdateFolder());
    }

    // Misc

    @Contract(pure = true)
    public static int getPort() {
        return Minecraft.server.getPort();
    }

    @Contract(pure = true)
    public static @NotNull String getIp() {
        return Minecraft.server.getIp();
    }

    @Contract(pure = true)
    public static long getConnectionThrottle() {
        return Minecraft.server.getConnectionThrottle();
    }

    @Contract(pure = true)
    public static int getTicksPerSpawnCategory(final SpawnCategory category) {
        return Minecraft.server.getTicksPerSpawns(category);
    }

    // Plugins

    // TODO: Custom class
    @Contract(pure = true)
    public static @NotNull PluginManager getPluginManager() {
        return Minecraft.server.getPluginManager();
    }

    // TODO: Custom class
    @Contract(pure = true)
    public static @NotNull BukkitScheduler getScheduler() {
        return Minecraft.server.getScheduler();
    }

    // TODO: Custom class
    public static @NotNull ServicesManager getServicesManager() {
        return Minecraft.server.getServicesManager();
    }


}
