package net.automodx;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.automodx.commands.AddUserCommand;
import net.automodx.commands.AddWordCommand;
import net.automodx.commands.RemoveUserCommand;
import net.automodx.commands.RemoveWordCommand;

public class AutoModX extends JavaPlugin {

    private List<String> bannedWords;
    private List<String> ignoredUsers;
    private File dataFile;

    @Override
    public void onEnable() {


        getLogger().info("AutoModX plugin enabled!!!!");
        
        
        File folder = new File(getDataFolder(), "automodx");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        dataFile = new File(folder, "automodx.txt");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Failed to create automodx.txt: " + e.getMessage());
            }
        }

        
        loadConfigData();

        
        if (getCommand("automodx") != null) {
            getCommand("automodx").setExecutor(new BaseCommand(this));
        }

        
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
    }

    @Override
    public void onDisable() {
        
        saveConfigData();
    }

    public void loadConfigData() {
        bannedWords = new ArrayList<>();
        ignoredUsers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            boolean isBannedWords = false;
            boolean isIgnoredUsers = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.equals("[BANNED_WORDS]")) {
                    isBannedWords = true;
                    isIgnoredUsers = false;
                } else if (line.equals("[IGNORED_USERS]")) {
                    isIgnoredUsers = true;
                    isBannedWords = false;
                } else {
                    if (isBannedWords) {
                        bannedWords.add(line);
                    } else if (isIgnoredUsers) {
                        ignoredUsers.add(line);
                    }
                }
            }
        } catch (IOException e) {
            getLogger().severe("Failed to load data from automodx.txt: " + e.getMessage());
        }
    }

    public void saveConfigData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFile))) {
            writer.write("[BANNED_WORDS]\n");
            for (String word : bannedWords) {
                writer.write(word + "\n");
            }

            writer.write("[IGNORED_USERS]\n");
            for (String user : ignoredUsers) {
                writer.write(user + "\n");
            }
        } catch (IOException e) {
            getLogger().severe("Failed to save data to automodx.txt: " + e.getMessage());
        }
    }

    public List<String> getBannedWords() {
        return bannedWords;
    }

    public List<String> getIgnoredUsers() {
        return ignoredUsers;
    }

    

    public class BaseCommand implements CommandExecutor {

        private final AutoModX plugin;

        public BaseCommand(AutoModX plugin) {
            this.plugin = plugin;
        }

        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (!(sender instanceof Player) || !sender.isOp()) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
                return true;
            }

            if (args.length == 0) {
                sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "Available commands: /automodx ping, addword, removeword, adduser, removeuser");
                return true;
            }

            switch (args[0].toLowerCase()) {
                case "ping":
                    sender.sendMessage(ChatColor.GOLD + "[AutoModX] " + ChatColor.WHITE + "Plugin is active.");
                    break;
                case "addword":
                    new AddWordCommand(plugin).execute(sender, args);
                    break;
                case "removeword":
                    new RemoveWordCommand(plugin).execute(sender, args);
                    break;
                case "adduser":
                    new AddUserCommand(plugin).execute(sender, args);
                    break;
                case "removeuser":
                    new RemoveUserCommand(plugin).execute(sender, args);
                    break;
                default:
                    sender.sendMessage(ChatColor.RED + "Unknown subcommand. Use /automodx for help.");
                    break;
            }
            return true;
        }
    }
}
