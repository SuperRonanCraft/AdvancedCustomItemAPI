package me.SuperRonanCraft.AdvancedCustomItemAPI;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.SuperRonanCraft.AdvancedCustomItemAPI.player.Commands;
import me.SuperRonanCraft.AdvancedCustomItemAPI.player.PlayerInfo;
import me.SuperRonanCraft.AdvancedCustomItemAPI.player.events.Listener;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.Messages;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.Permissions;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.Placeholders;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.web.Metrics;

import java.util.List;

public class Main extends JavaPlugin {

    public boolean PlaceholderAPI;
    private Messages text = new Messages(this);
    private Permissions perms = new Permissions();
    private Placeholders phd = new Placeholders(this);
    private Commands cmd = new Commands(this);
    private Listener listen = new Listener();
    private PlayerInfo playerInfo = new PlayerInfo();
    private static Main instance;

    public void onEnable() {
        instance = this;
        registerDependencies();
        registerConfig(false);
        registerUpdater();
        registerEvents();
        new Metrics(this);
    }

    public static Main getInstance() {
        return instance;
    }

    public Permissions getPerms() {
        return perms;
    }

    public Messages getText() {
        return text;
    }

    public Placeholders getPhd() {
        return phd;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public boolean papiEnabled() {
        return PlaceholderAPI;
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(listen, this);
    }

    private void registerUpdater() {
        listen.registerUpdater(!getConfig().getBoolean("Settings.Disable-Updater"));
    }

    private void registerConfig(boolean reload) {
        if (reload)
            reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        saveResource("updates.yml", true);
    }

    private void registerDependencies() {
        PlaceholderAPI = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    public Commands getCommand() {
        return cmd;
    }

    public Listener getListener() {
        return listen;
    }

    @Override
    public boolean onCommand(CommandSender sendi, Command cmd, String label, String[] args) {
        this.cmd.onCommand(sendi, label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sendi, Command cmd, String label, String[] args) {
        return this.cmd.onTabComplete(sendi, args);
    }

    public void reload(CommandSender sendi) {
        registerConfig(true);
        registerDependencies();
        registerUpdater();
        text.getReload(sendi);
    }
}
