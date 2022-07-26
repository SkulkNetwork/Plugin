package network.skulk.plugin.extensions.tpa;

import network.skulk.plugin.SkulkNetworkPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class OLD implements CommandExecutor {

    // K List<V>, V teleports to K.
    public HashMap<String, @Nullable ArrayList<String>> tpaRequests = new HashMap<>();

    // If "*" is in the value, the player ignores everyone.
    public HashMap<String, @Nullable HashSet<String>> tpaIgnores = new HashMap<>();

    public JavaPlugin plugin;

    public FileConfiguration config;

    public OLD(@NotNull SkulkNetworkPlugin plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();

        String[] commands = {"tpa", "tpa-reject", "tpa-accept", "tpa-cancel", "tpa-ignore", "tpa-ignore-all", "tpa-list-ignored"};

        for (String commandName : commands) {
            PluginCommand command = plugin.getCommand(commandName);

            if (command == null) {
                plugin.getLogger().severe("The command " + commandName + "could not be registered, it probably doesn't exist in the plugin.yml.");
                return;
            }

            command.setExecutor(this);
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This command can only be used by players.");
            return true;
        }

        String playerName = player.getName();

        switch (label.toLowerCase()) {
//            case "tpa" -> {
//                if (args.length != 1) return false;
//
//                Player target = Bukkit.getPlayer(playerName);
//
//                // The player doesn't exist or is offline.
//                if (target == null || !target.isOnline()) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This player is not online.");
//                    return true;
//                }
//
//                String targetName = target.getName();
//                HashSet<String> targetIgnores = tpaIgnores.get(targetName);
//
//                if (targetIgnores != null)
//                    if (targetIgnores.contains("*")) {
//                        // Here the target ignores everyone.
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> " + targetName + "</bold> doesn't accept TPA requests from anybody.");
//                        return true;
//                    } else if (targetIgnores.contains(playerName)) {
//                        // Here the target ignores the player.
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> " + targetName + "</bold> doesn't accept TPA requests from you.");
//                        return true;
//                    }
//
//
//                ArrayList<String> targetRequests = tpaRequests.get(targetName);
//
//                // The player already has a TPA request sent to the target.
//                if (targetRequests != null && targetRequests.contains(playerName)) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> You already have a pending TPA request to this player.");
//                    return true;
//                }
//
//                // Adds the request.
//                if (targetRequests != null)
//                    targetRequests.add(playerName);
//                else {
//                    targetRequests = new ArrayList<>();
//                    targetRequests.add(playerName);
//                    tpaRequests.put(targetName, targetRequests);
//                }
//
//                // Removing the request X seconds later.
//                Bukkit.getScheduler().runTaskLater(plugin, () -> {
//                    ArrayList<String> targetTpaRequests = tpaRequests.get(targetName);
//
//                    if (targetTpaRequests != null && targetTpaRequests.contains(playerName)) {
//                        targetTpaRequests.remove(playerName);
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> Your TPA request to <bold>" + targetName + "</bold> has expired.");
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> The TPA request <bold>" + playerName + "</bold> has sent to you has expired.");
//                    }
//                }, 60);
//
//                player.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> Sent a TPA request to <bold>" + targetName + "</bold>.");
//                target.sendRichMessage("<bold><gray>[ <cyan>?</cyan> ]</gray></bold> "
//                        + playerName + " has sent a TPA request to you. Do you accept? <bold><red><click:run_command:/tpa-accept "
//                        + playerName + ">[✓]</click></red><green><click:run_command:/tpa-reject "
//                        + playerName + ">[❌]</click></green></bold>");
//            }
//
//            case "tpa-reject" -> {
//                if (args.length > 1) return false;
//
//                String targetName;
//                ArrayList<String> playerRequests = tpaRequests.get(playerName);
//
//                if (args.length != 1) {
//
//                    if (playerRequests == null || playerRequests.size() == 0) {
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> You don't have any incoming TPA requests");
//                        return true;
//                    }
//
//                    if (playerRequests.size() != 1) {
//                        // Multiple people want to tpa.
//                        StringBuilder response = new StringBuilder()
//                                .append("<bold><gray>[ <blue>?</blue> ]</gray></bold> Seems like you have multiple people wanting to TPA to you. Which one would you like to reject?");
//
//                        for (String p : playerRequests)
//                            response.append("\n<bold><red><click:run_command:/tpa-reject ").append(p).append("[").append(p).append("]</click><red></bold>");
//
//
//                        player.sendRichMessage(response.toString());
//                        return true;
//
//                    } else {
//                        targetName = playerRequests.get(0);
//                    }
//                } else {
//                    targetName = args[0];
//                }
//
//                if (playerRequests == null || !playerRequests.contains(targetName)) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This person doesn't want to TPA to you.");
//                    return true;
//                }
//
//                Player target = Bukkit.getPlayer(targetName);
//
//                if (target == null || !target.isOnline()) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This player is not online.");
//                    return true;
//                }
//
//                playerRequests.remove(targetName);
//
//                player.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> Rejected <bold>" + targetName + "</bold>'s TPA request.");
//                target.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> " + playerName + "</bold> has rejected your TPA request.");
//            }
//
//            case "tpa-accept" -> {
//                if (args.length > 1) return false;
//
//                String targetName;
//                ArrayList<String> playerRequests = tpaRequests.get(playerName);
//
//                if (args.length != 1) {
//
//                    if (playerRequests == null || playerRequests.size() == 0) {
//                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> You don't have any incoming TPA requests.");
//                        return true;
//                    }
//
//                    if (playerRequests.size() != 1) {
//                        // Multiple people want to tpa.
//                        StringBuilder response = new StringBuilder()
//                                .append("<bold><gray>[ <blue>?</blue> ]</gray></bold> Seems like you have multiple people wanting to TPA to you. Which one would you like to accept?");
//
//                        for (String p : playerRequests)
//                            response.append("\n<bold><green><click:run_command:/tpa-accept ").append(p).append("[").append(p).append("]</click><green></bold>");
//
//                        player.sendRichMessage(response.toString());
//                        return true;
//
//                    } else {
//                        targetName = playerRequests.get(0);
//                    }
//                } else {
//                    targetName = args[0];
//                }
//
//                if (playerRequests == null || !playerRequests.contains(targetName)) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This person doesn't want to TPA to you.");
//                    return true;
//                }
//
//                Player target = Bukkit.getPlayer(targetName);
//
//                if (target == null || !target.isOnline()) {
//                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This player is not online.");
//                    return true;
//                }
//
//                player.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> Teleporting you to <bold>" + targetName + "</bold>...");
//                target.sendRichMessage("<bold><gray>[ <green>!</green> ]</gray></bold> Teleporting <bold>" + playerName + "</bold> to you...");
//
//                player.teleport(target);
//                playerRequests.remove(targetName);
//            }

            case "tpa-cancel" -> {
                if (args.length > 1) return false;

                String targetName;

                if (args.length == 0) {
                    ArrayList<String> playerOutgoingRequests = new ArrayList<>();

                    for (Map.Entry<String, ArrayList<String>> entry : tpaRequests.entrySet()) {
                        // Key is the target, the player will be in the value.
                        if (entry.getValue().contains(playerName)) {
                            playerOutgoingRequests.add(entry.getKey());
                        }
                    }

                    int playerOutgoingRequestsSize = playerOutgoingRequests.size();

                    if (playerOutgoingRequestsSize > 1) {
                        StringBuilder response = new StringBuilder("<bold><gray>[ <blue>?</blue> ]</gray></bold> Seems like you have multiple pending TPA requests. Which one would you like to cancel?");

                        for (String p : playerOutgoingRequests) {
                            response.append("\n<bold><color:#ffae1a><click:run_command:/tpa-cancel ").append(p).append("[").append(p).append("]</click></orange></bold>");
                        }

                        player.sendRichMessage(response.toString());
                        return true;
                    }

                    if (playerOutgoingRequestsSize == 0) {
                        player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> You don't have any outgoing TPA requests.");
                        return true;
                    }

                    targetName = playerOutgoingRequests.get(0);

                } else targetName = args[0];

                ArrayList<String> targetIncomingRequests = tpaRequests.get(targetName);

                if (targetIncomingRequests == null || !targetIncomingRequests.contains(playerName)) {
                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> You don't have an outgoing TPA request to <bold>" + targetName + "</bold>.");
                    return true;
                }

                Player target = Bukkit.getPlayer(targetName);

                targetIncomingRequests.remove(playerName);

                // TODO: Make this not be needed by removing requests when a player leaves.
                if (target == null || !target.isOnline()) {
                    player.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray></bold> This player is not online.");
                    return true;
                }
                player.sendRichMessage("<bold><gray>[ <green>✓</green> ]</gray></bold> Rejected <bold>" + targetName + "</bold>'s TPA request.");
                target.sendRichMessage("<bold><gray>[ <red>!</red> ]</gray> " + targetName + "</bold> has cancelled their TPA request to you.");
            }
        }
        return false;
    }
}
