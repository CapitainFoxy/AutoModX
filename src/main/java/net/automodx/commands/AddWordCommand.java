package net.automodx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.automodx.AutoModX;

public class AddWordCommand {

    private final AutoModX plugin;

    public AddWordCommand(AutoModX plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /automodx add <word>");
            return;
        }

        String word = args[1].toLowerCase();
        if (plugin.getBannedWords().contains(word)) {
            sender.sendMessage(ChatColor.RED + "This word is already in the banned list.");
            return;
        }

        plugin.getBannedWords().add(word);
        plugin.saveConfigData();  
        sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "The word '" + word + "' has been added to the banned list.");
    }
}
