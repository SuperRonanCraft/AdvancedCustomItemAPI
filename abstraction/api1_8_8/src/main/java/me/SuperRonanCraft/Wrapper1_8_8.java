package me.SuperRonanCraft;

import org.bukkit.Material;

public class Wrapper1_8_8 implements VersionWrapper {

    @Override
    public Material[] getPotions() {
        return new Material[]{Material.POTION};
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
