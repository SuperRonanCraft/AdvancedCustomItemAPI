package me.SuperRonanCraft.AdvancedCustomItemAPI.references.item;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.block.banner.PatternType;
import org.bukkit.entity.Player;

class Calculations {
    Color getColor(String raw) {
        try {
            String[] color = raw.split(",");
            return Color.fromRGB(val(color[0].trim()), val(color[1].trim()), val(color[2].trim()));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            try {
                return Color.fromRGB(val(raw));
            } catch (NumberFormatException e2) {
                try {
                    int r = Integer.valueOf(raw.substring(1, 3), 16);
                    int g = Integer.valueOf(raw.substring(3, 5), 16);
                    int b = Integer.valueOf(raw.substring(5, 7), 16);
                    return Color.fromRGB(r, g, b);
                } catch (NumberFormatException e3) {
                    int r = Integer.valueOf(raw.substring(0, 2), 16);
                    int g = Integer.valueOf(raw.substring(2, 4), 16);
                    int b = Integer.valueOf(raw.substring(4, 6), 16);
                    return Color.fromRGB(r, g, b);
                }
            }
        }
    }

    String checkPerms(String name, Player player) {
        if (name == null)
            return null;
        if (name.contains("{perm:")) {
            for (String perm : name.split("\\{perm:"))
                if (perm.endsWith("}")) {
                    perm = perm.replace("}", "");
                    String[] permChecks = perm.split(",");
                    for (String str : permChecks) {
                        str = str.trim();
                        if (str.startsWith("-")) {
                            if (player.hasPermission(str.replaceAll("-", "")))
                                return null;
                        } else if (!player.hasPermission(str))
                            return null;
                    }
                    perm = "{perm:" + perm + "}";
                    name = name.replaceAll(perm.replace("{", "\\{").replace("}", "\\}"), "");
                }
        }
        return name;
    }

    String checkCords(String name, Player player) {
        try {
            if (name.contains("{cords:")) {
                for (String cord : name.split("\\{cords:"))
                    if (cord.endsWith("}")) {
                        cord = cord.replace("}", "");
                        String[] parts = cord.split(",");
                        for (String part : parts) {
                            if (part.contains("x1=") && part.contains("x2=")) {
                                int x1 = Integer.valueOf(part.substring(part.indexOf("x1=") + 3, part.indexOf("x2="))
                                        .trim());
                                int x2 = Integer.valueOf(part.substring(part.indexOf("x2=") + 3).trim());
                                if (x1 >= player.getLocation().getBlockX() && x2 <= player.getLocation().getBlockX())
                                    continue;
                                else if (x1 <= player.getLocation().getBlockX() && x2 >= player.getLocation()
                                        .getBlockX())
                                    continue;
                                else
                                    return null;
                            }
                            if (part.contains("z1=") && part.contains("z2=")) {
                                int z1 = Integer.valueOf(part.substring(part.indexOf("z1=") + 3, part.indexOf("z2="))
                                        .trim());
                                int z2 = Integer.valueOf(part.substring(part.indexOf("z2=") + 3).trim());
                                if (z1 >= player.getLocation().getBlockZ() && z2 <= player.getLocation().getBlockZ())
                                    continue;
                                else if (z1 <= player.getLocation().getBlockZ() && z2 >= player.getLocation()
                                        .getBlockZ())
                                    continue;
                                else
                                    return null;
                            }
                            if (part.contains("y1=") && part.contains("y2=")) {
                                int y1 = Integer.valueOf(part.substring(part.indexOf("y1=") + 3, part.indexOf("y2="))
                                        .trim());
                                int y2 = Integer.valueOf(part.substring(part.indexOf("y2=") + 3).trim());
                                if (y1 >= player.getLocation().getBlockY() && y2 <= player.getLocation().getBlockY())
                                    continue;
                                else if (y1 <= player.getLocation().getBlockY() && y2 >= player.getLocation()
                                        .getBlockY())
                                    continue;
                                else
                                    return null;
                            }
                        }
                        cord = "{cords:" + cord + "}";
                        name = name.replaceAll(cord.replace("{", "\\{").replace("}", "\\}"), "");
                    }
            }
        } catch (NumberFormatException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&eAdvancedCustomMenu&7: &cWARNING &7Error while parsing " + name + ". &fPlease fix any issues "
                            + "with it!"));
            name = "&c&lERROR &f&oPlease contact an admin!";
        }
        return name;
    }

    PatternType patternType(String str) {
        if (str.equals("p"))
            return PatternType.GRADIENT;
        else if (str.equals("K"))
            return PatternType.GRADIENT_UP;
        else if (str.equals("e"))
            return PatternType.BRICKS;
        else if (str.equals("q"))
            return PatternType.HALF_HORIZONTAL;
        else if (str.equals("L"))
            return PatternType.HALF_HORIZONTAL_MIRROR;
        else if (str.equals("H"))
            return PatternType.HALF_VERTICAL;
        else if (str.equals("M"))
            return PatternType.HALF_VERTICAL_MIRROR;
        else if (str.equals("H"))
            return PatternType.HALF_VERTICAL;
        else if (str.equals("E"))
            return PatternType.STRIPE_TOP;
        else if (str.equals("f"))
            return PatternType.STRIPE_BOTTOM;
        else if (str.equals("s"))
            return PatternType.STRIPE_LEFT;
        else if (str.equals("y"))
            return PatternType.STRIPE_RIGHT;
        else if (str.equals("r"))
            return PatternType.DIAGONAL_LEFT;
        else if (str.equals("J"))
            return PatternType.DIAGONAL_LEFT_MIRROR;
        else if (str.equals("x"))
            return PatternType.DIAGONAL_RIGHT;
        else if (str.equals("I"))
            return PatternType.DIAGONAL_RIGHT_MIRROR;
        else if (str.equals("j"))
            return PatternType.CROSS;
        else if (str.equals("m"))
            return PatternType.STRIPE_DOWNLEFT;
        else if (str.equals("n"))
            return PatternType.STRIPE_DOWNRIGHT;
        else if (str.equals("z"))
            return PatternType.STRAIGHT_CROSS;
        else if (str.equals("l"))
            return PatternType.STRIPE_CENTER;
        else if (str.equals("w"))
            return PatternType.STRIPE_MIDDLE;
        else if (str.equals("C"))
            return PatternType.SQUARE_TOP_LEFT;
        else if (str.equals("b"))
            return PatternType.SQUARE_BOTTOM_LEFT;
        else if (str.equals("D"))
            return PatternType.SQUARE_TOP_RIGHT;
        else if (str.equals("d"))
            return PatternType.SQUARE_BOTTOM_RIGHT;
        else if (str.equals("F"))
            return PatternType.TRIANGLE_TOP;
        else if (str.equals("g"))
            return PatternType.TRIANGLE_BOTTOM;
        else if (str.equals("v"))
            return PatternType.RHOMBUS_MIDDLE;
        else if (str.equals("t"))
            return PatternType.CIRCLE_MIDDLE;
        else if (str.equals("h"))
            return PatternType.TRIANGLES_BOTTOM;
        else if (str.equals("G"))
            return PatternType.TRIANGLES_TOP;
        else if (str.equals("B"))
            return PatternType.STRIPE_SMALL;
        else if (str.equals("c"))
            return PatternType.BORDER;
        else if (str.equals("i"))
            return PatternType.CURLY_BORDER;
        else if (str.equals("o"))
            return PatternType.FLOWER;
        else if (str.equals("k"))
            return PatternType.CREEPER;
        else if (str.equals("A"))
            return PatternType.SKULL;
        else if (str.equals("u"))
            return PatternType.MOJANG;
        return null;
    }

    private int val(String str) {
        return Integer.valueOf(str);
    }
}
