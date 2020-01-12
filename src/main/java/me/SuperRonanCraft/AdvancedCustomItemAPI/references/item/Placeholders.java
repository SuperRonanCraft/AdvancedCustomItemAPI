package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;
import me.SuperRonanCraft.AdvancedCustomItemAPI.player.enums.ItemType;

public class Placeholders {
    private Main pl;
    private InvItemMeta meta = new InvItemMeta();

    public Placeholders(Main pl) {
        this.pl = pl;
    }

    @SuppressWarnings("all")
    public ItemStack createItem(Player p, String[] type, String name, List<String> lore) {
        try {
            if (type.length == 1)
                if (p != null && pl.papiEnabled())
                    return meta.addMeta(p, new ItemStack(mat(meta.placeholders(type[0], p)), 1, (short) 0), name, lore);
                else
                    return meta.addMeta(p, new ItemStack(mat(type[0]), 1, (short) 0), name, lore);
            else if (type.length == 2)
                if (p != null && pl.papiEnabled())
                    return meta.addMeta(p, new ItemStack(mat(meta.placeholders(type[0], p)), val(type[1].trim()),
                            (short) 0), name, lore);
                else
                    return meta.addMeta(p, new ItemStack(mat(type[0]), val(type[1].trim()), (short) 0), name, lore);
            else if (type.length == 3)
                if (p != null && pl.papiEnabled())
                    return meta.addMeta(p, new ItemStack(mat(meta.placeholders(type[0], p)), val(meta.placeholders
                            (type[1], p)), (short) val(meta.placeholders(type[2], p))), name, lore);
                else
                    return meta.addMeta(p, new ItemStack(mat(type[0]), val(type[1].trim()), (short) val(type[2])),
                            name, lore);
        } catch (NullPointerException | IllegalArgumentException e) {
            return invalidItem(type);
        }
        return invalidItem(type);
    }

    @SuppressWarnings("unused")
    public void setBanner(ItemStack item, String pattern) {
        meta.setBanner(item, pattern);
    }

    @SuppressWarnings("unused")
    public Inventory createInventory(int rows, InventoryType type, String title) {
        if (type.equals(InventoryType.CHEST))
            return Bukkit.getServer().createInventory(null, rows != 0 ? rows : 27, title);
        return Bukkit.getServer().createInventory(null, type, title);
    }

    @SuppressWarnings({"unused", "deprecated"})
    public void addEnchants(ItemStack item, List<String> enchants) {
        if (enchants != null && !enchants.isEmpty())
            try {
                for (String ench : enchants) {
                    if (Enchantment.getByName(ench.split(":")[0].trim().toUpperCase()) != null) {
                        int lvl = val(ench.trim().split(":")[1]);
                        item.addUnsafeEnchantment(Enchantment.getByName(ench.split(":")[0].trim().toUpperCase()), lvl);
                        //item.getItemMeta().removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                    } else
                        pl.getText().enchantError(ench.split(":")[0].trim());
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
    }

    @SuppressWarnings("unused")
    public void hideFlags(ItemStack item, int flag) {
        ItemMeta meta = item.getItemMeta();
        StringBuilder buf1 = new StringBuilder();
        StringBuilder buf2 = new StringBuilder();
        while (flag != 0) {
            int digit = flag % 2;
            buf1.append(digit);
            flag = flag / 2;
        }
        String binary = buf1.reverse().toString();// reverse to get binary 1010
        int length = binary.length();
        if (length < 6)
            while (6 - length > 0) {
                buf2.append("0");// add zero until length =8
                length++;
            }
        String bin = buf2.toString() + binary;// binary string with leading 0's
        if (bin.substring(5, 6).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if (bin.substring(4, 5).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS);
        if (bin.substring(3, 4).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        if (bin.substring(2, 3).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_PLACED_ON);
        if (bin.substring(1, 2).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
        if (bin.substring(0, 1).equals("1"))
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);
    }

    @SuppressWarnings("unused")
    public void setNbt(ItemStack item, ItemType type, String nbt, Player p) {
        nbt = pl.getText().color(p, nbt);
        if (type.equals(ItemType.BANNER))
            meta.setBanner(item, nbt);
        else if (type.equals(ItemType.COLOR))
            meta.setColor(item, nbt);
        else if (type.equals(ItemType.MONSTER_EGG))
            return;
            //meta.setEgg(item, nbt);
        else if (type.equals(ItemType.SKULL))
            meta.setHead(item, nbt);
    }

    private ItemStack invalidItem(String[] item) {
        List<String> lore = new ArrayList<>();
        if (item != null)
            if (item.length == 1)
                lore.add(pl.getText().color(null, "&f&n" + item[0]));
            else if (item.length == 2)
                lore.add(pl.getText().color(null, "&f&n" + item[0] + ":" + item[1]));
            else {
                String itemid = null;
                for (String str : item) {
                    if (itemid == null)
                        itemid = str;
                    else
                        itemid = itemid + ":" + str;
                }
                lore.add(pl.getText().color(null, "&f&n" + itemid));
            }
        return createItem(null, ("Bedrock").split(":"), "&c&lInvalid Item Id", lore);
    }

    private Material mat(String str) {
        return Material.getMaterial(str.toUpperCase().trim());
    }

    private int val(String str) {
        return Integer.valueOf(str.trim());
    }
}