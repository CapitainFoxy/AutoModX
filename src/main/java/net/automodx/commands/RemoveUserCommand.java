package net.automodx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.automodx.AutoModX;

public class RemoveUserCommand {

    private final AutoModX plugin;

    public RemoveUserCommand(AutoModX plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /automodx removeuser <username>");
            return;
        }

        String username = args[1].toLowerCase();
        if (!plugin.getIgnoredUsers().contains(username)) {
            sender.sendMessage(ChatColor.RED + "This user is not in the ignored list.");
            return;
        }

        plugin.getIgnoredUsers().remove(username);
        plugin.saveConfigData();  
        sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "The user '" + username + "' has been removed from the ignored list.");
    }
}
