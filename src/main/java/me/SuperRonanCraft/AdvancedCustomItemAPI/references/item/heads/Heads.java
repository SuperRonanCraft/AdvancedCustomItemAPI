package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.heads;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTCompound;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTItem;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTListCompound;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.item.NBT.NBTType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class Heads {

    private HashMap<String, ItemStack> cache = new HashMap<>();
    private Field field;


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
                    getCustomHead(item, nbt);
                } catch (Exception e2) {
                    //e2.printStackTrace();
                }
                /*try {
                    urlHead(item, nbt);
                } catch (Exception e2) {
                    NBTHead(item, nbt);
                }*/
            }
        }
    }

    public void getCustomHead(ItemStack skull, String url) {

        try {
            //1.13
            skull.setType(Material.valueOf("PLAYER_HEAD"));
        } catch (Exception e) { //Deprecation support
            //1.8-1.12
            skull.setType(Material.valueOf("SKULL_ITEM"));
            skull.setDurability((short) 3);
        }
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();

        assert skullMeta != null;

        if (url.length() < 16) {

            skullMeta.setOwner(url);
            skull.setItemMeta(skullMeta);
            return;
        }

        StringBuilder s_url = new StringBuilder();
        s_url.append("https://textures.minecraft.net/texture/").append(url); // We get the texture link.

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null); // We create a GameProfile

        // We get the bytes from the texture in Base64 encoded that comes from the Minecraft-URL.
        byte[] data = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", s_url.toString()).getBytes());

        // We set the texture property in the GameProfile.
        gameProfile.getProperties().put("textures", new Property("textures", new String(data)));

        try {

            if (field == null) field = skullMeta.getClass().getDeclaredField("profile"); // We get the field profile.

            field.setAccessible(true); // We set as accessible to modify.
            field.set(skullMeta, gameProfile); // We set in the skullMeta the modified GameProfile that we created.

        } catch (Exception e) {
            e.printStackTrace();
        }

        skull.setItemMeta(skullMeta);
        cache.put(url, skull);

        //return skull; //Finally, you have the custom head!

    }

    /*private void textureHead(ItemStack item, String nbt) {
        try {
            //1.13
            item.setType(Material.valueOf("LEGACY_SKULL_ITEM"));
        } catch (Exception e) { //Deprecation support
            //1.8-1.12
            item.setType(Material.valueOf("SKULL_ITEM"));
            item.setDurability((short) 3);
        }
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), ""); //Create a dummy profile
        profile.getProperties().put("textures", new Property("textures", nbt));
        Field profileField = null;
        try { //Find the skull properties and set the texture
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
            //System.out.println("Texture set! " + nbt);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        item.setItemMeta(headMeta); //Set the texture
    }*/

    private void playerHead(ItemStack item, String nbt) throws IOException {
        SkullMeta sMeta = (SkullMeta) item.getItemMeta();
        UUID id = UUID.fromString(nbt);
        OfflinePlayer player = Bukkit.getOfflinePlayer(id);
        if (sMeta.setOwner(player.getName())) {
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
                head = new ItemStack(Material.valueOf("PLAYER_HEAD"), 1, (short) 3);
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

    /*private void urlHead(ItemStack item, String url) throws Exception {
        System.out.println(url);
        SkullMeta headMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        //URL url_0 = new URL(url);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url.trim()).getBytes
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
    }*/

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
