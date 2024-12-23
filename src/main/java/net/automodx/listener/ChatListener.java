package net.automodx.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final AutoModX plugin;

    public ChatListener(AutoModX plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
    
        if (plugin.getIgnoredUsers().contains(event.getPlayer().getName().toLowerCase())) {
            return;
        }

        
        String message = event.getMessage().toLowerCase();


        for (String bannedWord : plugin.getBannedWords()) {
            if (message.contains(bannedWord.toLowerCase())) {
                
                event.setCancelled(true);

                
                event.getPlayer().sendMessage(ChatColor.RED + "You cannot use the word '" + bannedWord + "'.");

                
                return;
            }
        }
    }
}
