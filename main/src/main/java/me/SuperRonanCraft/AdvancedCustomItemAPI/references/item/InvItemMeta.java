package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;
import me.SuperRonanCraft.VersionWrapper;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.heads.Heads;
import me.SuperRonanCraft.Wrapper;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class InvItemMeta {
    private final Calculations cal = new Calculations();
    private Material[] valids;
    private final Material[] leathers = {Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
            Material.LEATHER_LEGGINGS};
    private Material[] potions;
    private Material arrow;
    private final Heads heads = new Heads();

    InvItemMeta() {
        // 1.8.x Compatibility
        VersionWrapper wrapper = Wrapper.getWrapper();
        valids = wrapper.getColorable();
        potions = wrapper.getPotions();
        arrow = wrapper.getArrow();
        /*try {
            Material[] valids = {Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
                    Material.LEATHER_LEGGINGS, Material.POTION, Material.SPLASH_POTION, Material.TIPPED_ARROW,
                    Material.LINGERING_POTION};
            Material[] potions = {Material.POTION, Material.SPLASH_POTION, Material.LINGERING_POTION};
            this.arrow = Material.TIPPED_ARROW;
            this.potions = potions;
            this.valids = valids;
        } catch (NoSuchFieldError e) {
            Material[] valids = {Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET,
                    Material.LEATHER_LEGGINGS, Material.POTION};
            this.potions = new Material[]{Material.POTION};
            this.valids = valids;
        }*/
    }

    void setColor(ItemStack item, String colors) {
        if (colors.isEmpty())
            return;
        boolean leather = false;
        boolean potion = false;
        boolean valid = false;
        for (Material mat : valids)
            if (item.getType().equals(mat)) {
                valid = true;
                break;
            }
        if (!valid)
            if (new Random().nextInt(2) == 1)
                item.setType(leathers[new Random().nextInt(leathers.length)]);
            else
                item.setType(potions[new Random().nextInt(potions.length)]);
        for (Material mat : leathers)
            if (mat.equals(item.getType())) {
                leather = true;
                break;
            }
        if (!leather)
            for (Material mat : potions)
                if (mat.equals(item.getType())) {
                    potion = true;
                    break;
                }
        if (!potion && !leather)
            if (item.getType().equals(this.arrow))
                potion = true;
        if (leather) {
            LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
            meta.setColor(cal.getColor(colors));
            item.setItemMeta(meta);
        } else if (potion) {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            //meta.setColor(cal.getColor(colors));
            item.setItemMeta(meta);
        }
    }

    @SuppressWarnings("deprecation")
    void setBanner(ItemStack item, String pattern) {
        if (!pattern.isEmpty()) {
            try {
                //1.13
                item.setType(Material.valueOf("LEGACY_BANNER"));
            } catch (Exception e) {
                //1.8-1.12
                item.setType(Material.valueOf("BANNER"));
            }
            BannerMeta bmeta = (BannerMeta) item.getItemMeta();
            List<Pattern> patterns = new ArrayList<>();
            patterns.add(new Pattern(DyeColor.getByDyeData((byte) dyeColor(pattern.substring(0, 1))),
                    PatternType.BASE));
            for (int i = 2; i < pattern.length(); i = i + 2)
                patterns.add(new Pattern(DyeColor.getByDyeData((byte) dyeColor(pattern.substring(i, i + 1))),
                        cal.patternType(pattern.substring(i + 1, i + 2))));
            bmeta.setPatterns(patterns);
            item.setItemMeta(bmeta);
        }
    }
/*
    @SuppressWarnings("deprecation")
    void setEgg(ItemStack item, String entity) {
        try {
            if (!entity.isEmpty()) {
                try {
                    //1.13
                    item.setType(Material.valueOf("LEGACY_MONSTER_EGGS"));
                } catch (Exception e) {
                    //1.8-1.12
                    item.setType(Material.valueOf("MONSTER_EGG"));
                }
                SpawnEggMeta eggmeta = (SpawnEggMeta) item.getItemMeta();
                eggmeta.setSpawnedType(EntityType.valueOf(entity.toUpperCase()));
                item.setItemMeta(eggmeta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    @SuppressWarnings("deprecation")
    void setHead(ItemStack item, String owner) {
        if (!owner.isEmpty()) {
            try {
                //1.13
                item.setType(Material.valueOf("PLAYER_HEAD"));
            } catch (Exception e) {
                //1.8-1.12
                item.setType(Material.valueOf("SKULL_ITEM"));
            }
            if (item.getDurability() != 3)
                item.setDurability((short) 3);
            heads.setHead(item, owner);
        }
    }

    ItemStack addMeta(Player p, ItemStack item, String name, List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (lore != null)
                meta.setLore(lore);
            if (name != null)
                meta.setDisplayName(name);
            item.setItemMeta(meta);
        }
        checkPH(item, p);
        return item;
    }

    private void checkPH(ItemStack item, Player player) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null)
            return;
        meta.setDisplayName(placeholders(meta.getDisplayName(), player));
        // Lore
        List<String> lore = item.getItemMeta().getLore();
        if (lore != null && !lore.isEmpty()) {
            List<String> newLore = new ArrayList<>();
            for (int i = 0; i < meta.getLore().size(); i++) {
                try {
                    String str = placeholders(lore.get(i), player);
                    if (str != null)
                        newLore.add(str);
                } catch (NullPointerException e) {
                    newLore.add(lore.get(i));
                }
            }
            meta.setLore(newLore);
        }
        item.setItemMeta(meta);
    }

    String placeholders(String name, Player player) {
        name = cal.checkPerms(name, player);
        if (name != null)
            name = cal.checkCords(name, player);
        if (name == null)
            return null;
        return Main.getInstance().getText().colorPre(player, name);
    }

    private int dyeColor(String str) {
        return ("abcdefghijklmnop").indexOf(str);
    }
}
