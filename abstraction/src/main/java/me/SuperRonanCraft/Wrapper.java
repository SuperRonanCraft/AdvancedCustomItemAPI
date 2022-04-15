package me.SuperRonanCraft;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Bukkit;

public class Wrapper {

    @NotNull
    public static VersionWrapper getWrapper() {
        String version = Bukkit.getBukkitVersion().split("-")[0];
        switch(version)
        {
            case "1.18": return new Wrapper1_18_2();
            case "1.17": return new Wrapper1_17_1();
            case "1.16": return new Wrapper1_16_5();
            case "1.15": return new Wrapper1_15_2();
            case "1.14": return new Wrapper1_14_4();
            case "1.13": return new Wrapper1_13_2();
            case "1.12": return new Wrapper1_12_2();
            case "1.11": return new Wrapper1_11_2();
            case "1.10": return new Wrapper1_10_2();
            case "1.9": return new Wrapper1_9_4();
            case "1.8": return new Wrapper1_8_8();
            default:
                Bukkit.getLogger().warning("[AdvancedCustomItemAPI] This version of minecraft v" + version + " is not supported!");
                break;
        }
        return new DefaultWrapper();
    }

}
