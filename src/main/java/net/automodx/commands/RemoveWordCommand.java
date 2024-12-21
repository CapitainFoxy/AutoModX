package net.automodx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.automodx.AutoModX;

public class RemoveWordCommand {

    private final AutoModX plugin;

    public RemoveWordCommand(AutoModX plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /automodx remove <word>");
            return;
        }

        String word = args[1].toLowerCase();
        if (!plugin.getBannedWords().contains(word)) {
            sender.sendMessage(ChatColor.RED + "This word is not in the banned list.");
            return;
        }

        plugin.getBannedWords().remove(word);
        plugin.saveConfigData();  
        sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "The word '" + word + "' has been removed from the banned list.");
    }
}
