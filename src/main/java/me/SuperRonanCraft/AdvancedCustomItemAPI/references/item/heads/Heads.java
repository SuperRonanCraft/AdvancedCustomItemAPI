package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.heads;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTCompound;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTItem;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTList;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTListCompound;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTType;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

public class Heads {

    private HashMap<String, ItemStack> cache = new HashMap<>();


    public void setHead(ItemStack item, String nbt) {
        if (cache.containsKey(nbt)) {
            ItemStack newItem = cache.get(nbt);
            ItemMeta meta = newItem.getItemMeta();
            meta.setDisplayName(item.getItemMeta().getDisplayName());
            meta.setLore(item.getItemMeta().getLore());
            item.setItemMeta(meta);
        } else {
            try {
                playerHead(item, nbt);
            } catch (Exception e) {
                try {
                    textureHead(item, nbt);
                } catch (Exception e2) {

                }
                /*try {
                    urlHead(item, nbt);
                } catch (Exception e2) {
                    NBTHead(item, nbt);
                }*/
            }
        }
    }

    private void textureHead(ItemStack item, String nbt) throws IOException {
        GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(), null);
        newSkinProfile.getProperties().put("textures", new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + nbt + "\"}}}")));
        PropertyMap p = newSkinProfile.getProperties();
        String texture = p.get("value").toString();
        String signature = p.get("signature").toString();
        String[] headTexture = new String[]{texture, signature, newSkinProfile.getId().toString()};
        setHead(item, nbt, headTexture);
    }

    @SuppressWarnings("deprecation")
    private void playerHead(ItemStack item, String nbt) throws IOException {
        SkullMeta sMeta = (SkullMeta) item.getItemMeta();
        if (sMeta.setOwner(nbt)) {
            item.setItemMeta(sMeta);
            cache.put(nbt, item);
            return;
        }
        String[] headTexture = getFromName(nbt);
        setHead(item, nbt, headTexture);
    }

    private void setHead(ItemStack item, String nbt, String[] headTexture) throws IOException {
        if (headTexture != null) {
            ItemStack head;
            try {
                //1.13
                head = new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM"), 1, (short) 3);
            } catch (Exception e) {
                //1.8-1.12
                head = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
            }
            head.setItemMeta(item.getItemMeta());
            NBTItem nbti = new NBTItem(head);
            NBTCompound skull = nbti.addCompound("SkullOwner");
            skull.setString("Id", headTexture[2]);
            NBTListCompound texture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound)
                    .addCompound();
            texture.setString("Signature", headTexture[1]);
            texture.setString("Value", headTexture[0]);
            head = nbti.getItem();
            item.setItemMeta(head.getItemMeta());
            cache.put(nbt, item);
        } else
            throw new IOException();
    }

    private void urlHead(ItemStack item, String url) throws Exception {
        System.out.println(url);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        //URL url_0 = new URL(url);
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url.trim()).getBytes
                ());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            throw new MalformedURLException();
        }
        item.setItemMeta(headMeta);
        cache.put(url, item);
    }


    @SuppressWarnings("deprecation")
    private void NBTHead(ItemStack item, String nbt) {
        try {
            ItemStack head;
            try {
                //1.13
                head = new ItemStack(Material.valueOf("LEGACY_SKULL_ITEM"), 1, (short) 3);
            } catch (Exception e) {
                //1.8-1.12
                head = new ItemStack(Material.valueOf("SKULL_ITEM"), 1, (short) 3);
            }
            NBTItem nbti = new NBTItem(head);
            NBTCompound disp = nbti.addCompound("display");
            disp.setString("Name", "INVALID SKULL");
            NBTList l = disp.getList("Lore", NBTType.NBTTagString);
            l.addString("Some lore");
            NBTCompound skull = nbti.addCompound("SkullOwner");
            skull.setString("Name", "tr7zw");
            skull.setString("Id", "fce0323d-7f50-4317-9720-5f6b14cf78ea");
            NBTListCompound texture = skull.addCompound("Properties").getList("textures", NBTType.NBTTagCompound)
                    .addCompound();
            texture.setString("Signature", "XpRfRz6/vXE6ip7/vq+40H6W70GFB0yjG6k8hG4pmFdnJFR+VQhslE0gXX/i0OAGThcAVSIT"
                    + "+/W1685wUxNofAiy+EhcxGNxNSJkYfOgXEVHTCuugpr" +
                    "+EQCUBI6muHDKms3PqY8ECxdbFTUEuWxdeiJsGt9VjHZMmUukkGhk0IobjQS3hjQ44FiT1tXuUU86oAxqjlKFpXG" +
                    "/iXtpcoXa33IObSI1S3gCKzVPOkMGlHZqRqKKElB54I2Qo4g5CJ+noudIDTzxPFwEEM6XrbM0YBi+SOdRvTbmrlkWF" +
                    "+ndzVWEINoEf++2hkO0gfeCqFqSMHuklMSgeNr/YtFZC5ShJRRv7zbyNF33jZ5DYNVR+KAK9iLO6prZhCVUkZxb1" +
                    "/BjOze6aN7kyN01u3nurKX6n3yQsoQQ0anDW6gNLKzO/mCvoCEvgecjaOQarktl/xYtD4YvdTTlnAlv2bfcXUtc" +
                    "++3UPIUbzf" + "/jpf2g2wf6BGomzFteyPDu4USjBdpeWMBz9PxVzlVpDAtBYClFH/PFEQHMDtL5Q" +
                    "+VxUPu52XlzlUreMHpLT9EL92xwCAwVBBhrarQQWuLjAQXkp3oBdw6hlX6Fj0AafMJuGkFrYzcD7nNr61l9ErZmTWnqTxkJWZfZxmYBsFgV35SKc8rkRSHBNjcdKJZVN4GA+ZQH5B55mi4=");
            texture.setString("Value",
                    "eyJ0aW1lc3RhbXAiOjE0OTMwNDkwMTcxNTIsInByb2ZpbGVJZCI6ImZjZTAzMjNkN2Y1MDQzMTc5NzIwNWY2YjE0Y2Y3OGVhIiwicHJvZmlsZU5hbWUiOiJ0cjd6dyIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTI3NDZlNWU5OGMwZWRmZTU1YTI3ZGRjNjUxMmJmNjllYzJiYmNlNmM3ZmNhNTQ5YmEzNjZkYThiNTRjZTRkYiJ9fX0=");
            NBTList attribute = nbti.getList("AttributeModifiers", NBTType.NBTTagCompound);
            NBTListCompound mod1 = attribute.addCompound();
            mod1.setInteger("Amount", 10);
            mod1.setString("AttributeName", "generic.maxHealth");
            mod1.setString("Name", "generic.maxHealth");
            mod1.setInteger("Operation", 0);
            mod1.setInteger("UUIDLeast", 59664);
            mod1.setInteger("UUIDMost", 31453);
            nbti.setInteger("HideFlags", 4);
            nbti.setBoolean("Unbreakable", true);
            head = nbti.getItem();
            item.setItemMeta(head.getItemMeta());
        } catch (Exception e) {
            //Nothing
        }
    }

    private String[] getFromName(String name) {
        try {
            URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name.trim());
            InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
            String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
            URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid +
                    "?unsigned=false");
            InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
            JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties")
                    .getAsJsonArray().get(0).getAsJsonObject();
            String texture = textureProperty.get("value").getAsString();
            String signature = textureProperty.get("signature").getAsString();
            return new String[]{texture, signature, uuid};
        } catch (IOException | IllegalStateException e) {
            return null;
        }
    }
}
