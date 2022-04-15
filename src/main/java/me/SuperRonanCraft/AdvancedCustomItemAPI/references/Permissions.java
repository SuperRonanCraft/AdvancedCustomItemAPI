package me.SuperRonanCraft.AdvancedCustomItemAPI.references;

import org.bukkit.command.CommandSender;

public class Permissions {

    public boolean getUse(CommandSender sendi) {
        return perm("advancedcustomitemapi.use", sendi);
    }

    public boolean getUpdate(CommandSender sendi) {
        return perm("advancedcustomitemapi.updater", sendi);
    }

    public boolean getReload(CommandSender sendi) {
        return perm("advancedcustomitemapi.reload", sendi);
    }

    private boolean perm(String str, CommandSender sendi) {
        return sendi.hasPermission(str);
    }
}
