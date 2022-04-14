package me.SuperRonanCraft;

import me.SuperRonanCraft.AdvancedCustomItemAPI.references.VersionWrapper;
import org.bukkit.Bukkit;

public class Wrapper {

    public VersionWrapper getWrapper() {
        String version = Bukkit.getBukkitVersion().split("-")[0];
        switch(version)
        {
            case "1.14": return null;
            case "1.13": return null;
            case "1.12": return null;
            case "1.8": return new Wrapper1_8_8();
            default:
                Bukkit.getLogger().warning("[AdvancedCustomItemAPI] This version of minecraft v" + version + " is not supported!");
                break;
        }
        return null;
    }

}
