package me.SuperRonanCraft;

import com.avaje.ebean.validation.NotNull;
import org.bukkit.Material;

public interface VersionWrapper {

    @NotNull
    Material[] getPotions();

    @NotNull
    Material[] getColorable();

    @NotNull
    Material getArrow();

}
