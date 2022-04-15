package me.SuperRonanCraft;

import org.bukkit.Material;

public class Wrapper1_12_2 implements VersionWrapper {

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
        return Material.ARROW;
    }
}
