package me.SuperRonanCraft;

import org.bukkit.Material;

public class Wrapper1_9_4 implements VersionWrapper {
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
