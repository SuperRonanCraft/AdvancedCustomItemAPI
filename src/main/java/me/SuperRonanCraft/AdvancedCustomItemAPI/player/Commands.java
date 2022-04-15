package me.SuperRonanCraft.AdvancedCustomItemAPI.player;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;

import java.util.ArrayList;
import java.util.List;

public class Commands {

    private Main pl;

    public Commands(Main pl) {
        this.pl = pl;
    }

    private String[] cmds = {"reload", "help", "version"};

    public void onCommand(CommandSender sendi, String label, String[] args) {
        if (!pl.getPerms().getUse(sendi)) {
            pl.getText().getNoPermission(sendi);
            return;
        }
        if (args.length != 0) {
            if (args[0].equalsIgnoreCase(cmds[0]))
                reload(sendi);
            else if (args[0].equalsIgnoreCase(cmds[1]))
                help(sendi, label);
            else if (args[0].equalsIgnoreCase(cmds[2]))
                version(sendi);
            else
                invalid(sendi, label);
        } else
            help(sendi, label);
    }

    public List<String> onTabComplete(CommandSender sendi, String[] args) {
        if (!pl.getPerms().getUse(sendi))
            return null;
        List<String> list = new ArrayList<>();
        if (args.length == 1) { // All
            for (String str : cmds)
                if (str.toLowerCase().startsWith(args[0].toLowerCase()) && permOf(sendi, str))
                    list.add(str);
        }
        return list;
    }

    private void reload(CommandSender sendi) {
        if (perm(pl.getPerms().getReload(sendi), sendi))
            pl.reload(sendi);
    }

    private void help(CommandSender sendi, String label) {
        if (!(sendi instanceof Player))
            sendi.sendMessage(pl.getText().color(null, "&cWARNING&7: &fConsole may not be able to execute all of the " +
                    "" + "" + "" + "" + "" + "" + "" + "following commands!"));
        sendi.sendMessage(pl.getText().color(sendi, "&e&m------&r &6&lAdvancedCustomItemAPI &8| &7Help &e&m------"));
        if (pl.getPerms().getReload(sendi))
            sendi.sendMessage(pl.getText().color(sendi, " &7- &e/" + label + " reload&7: Reloads the config!"));
        sendi.sendMessage(pl.getText().color(sendi, " &7- &e/" + label + " version&7: View current version!"));
    }

    private void version(CommandSender sendi) {
        sendi.sendMessage(pl.getText().colorPre(sendi, "%prefix% &aVersion #&e" + pl.getDescription().getVersion()));
    }

    private boolean perm(Boolean perm, CommandSender sendi) {
        if (!perm) {
            pl.getText().getNoPermission(sendi);
            return false;
        }
        return true;
    }

    private boolean permOf(CommandSender sendi, String cmd) {
        if (cmd.equalsIgnoreCase(cmds[0]))
            return perm(pl.getPerms().getReload(sendi), sendi);
        else if (cmd.equalsIgnoreCase(cmds[1]))
            return perm(pl.getPerms().getUse(sendi), sendi);
        else if (cmd.equalsIgnoreCase(cmds[2]))
            return perm(pl.getPerms().getUse(sendi), sendi);
        return false;
    }

    private void invalid(CommandSender sendi, String label) {
        pl.getText().getInvalid(sendi, label);
    }
}
