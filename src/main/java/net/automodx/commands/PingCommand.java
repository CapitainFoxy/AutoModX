package net.automodx.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import net.automodx.AutoModX;

public class PingCommand {

    private final AutoModX plugin;

    public PingCommand(AutoModX plugin) {
        this.plugin = plugin;
    }

    public void execute(CommandSender sender, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return;
        }

        long startTime = System.currentTimeMillis();

        
        sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "Pinging plugin...");
        
        long endTime = System.currentTimeMillis();
        long ping = endTime - startTime;

        sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "Plugin ping: " + ChatColor.GREEN + ping + "ms");
    }
}
