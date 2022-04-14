package me.SuperRonanCraft;

import org.bukkit.Material;

public class DefaultWrapper implements VersionWrapper {

    @Override
    public Material[] getPotions() {
        return new Material[0];
    }

    @Override
    public Material[] getColorable() {
        return new Material[0];
    }

    @Override
    public Material getArrow() {
        return null;
    }
}
