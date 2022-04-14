package me.SuperRonanCraft.AdvancedCustomItemAPI.references;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;
import me.SuperRonanCraft.AdvancedCustomItemAPI.player.enums.ArgumentType;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Messages {
    private Main plugin;
    private String msg = "Messages.";

    public Messages(Main pl) {
        plugin = pl;
    }

    public void getReload(CommandSender p) {
        smsPre(p, plugin.getConfig().getString(msg + "Reload"));
    }

    public void getNoPermission(CommandSender p) {
        smsPre(p, plugin.getConfig().getString(msg + "NoPermission"));
    }

    public void getInvalid(CommandSender p, String cmd) {
        smsPre(p, plugin.getConfig().getString(msg + "Invalid").replaceAll("%command%", cmd));
    }

    private String getPrefix() {
        return plugin.getConfig().getString(msg + "Prefix");
    }

    private void smsPre(CommandSender sendi, String str) {
        sendi.sendMessage(colorPre(sendi, str));
    }

    public void enchantError(String ench) {
        Bukkit.getConsoleSender().sendMessage(colorPre(Bukkit.getConsoleSender(), "%prefix% &cThe Enchant '" + ench +
                "' is Invalid! &eVisit https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments"));
    }

    public String color(CommandSender p, String str) {
        if (p != null && str != null && p instanceof Player) {
            if (str.contains("%argument_"))
                for (int i = 0; i < str.split("%argument_").length; i++) {
                    String arg = str.substring(str.indexOf("%argument_"), str.indexOf("%", str.indexOf("%argument_")
                            + 1) + 1);
                    str = str.replace(arg, arguments(p, arg));
                }
            if (str.contains("<") && str.contains(">"))
                for (int i = 0; i < str.split("<").length; i++) {
                    String eval = str.substring(str.indexOf("<"), str.indexOf(">", str.indexOf("<") + 1) + 1);
                    try {
                        str = str.replace(eval, String.valueOf(eval(eval.replace("<", "").replaceAll(">", ""))));
                    } catch (RuntimeException e) {
                        // Bukkit.getConsoleSender().sendMessage(colorPre(null,"%prefix%
                        // &c&lInvalid Equation: &7'&e" + eval.replace("<",
                        // "").replace(">", "") + "&7'"));
                    }
                }
            str = getPh(p, str);
        }
        if (str != null)
            return ChatColor.translateAlternateColorCodes('&', str);
        return null;
    }

    private String getPh(CommandSender p, String str) {
        if (plugin.PlaceholderAPI)
            try {
                str = PlaceholderAPI.setPlaceholders((Player) p, str);
            } catch (Exception e) {
                //Something went wrong with PAPI
            }
        if (str.contains("%player_name%"))
            str = str.replaceAll("%player_name%", p.getName());
        if (str.contains("%player_uuid%"))
            if (p instanceof Player)
                str = str.replaceAll("%player_uuid%", ((Player) p).getUniqueId().toString());
        return str;
    }

    @SuppressWarnings("deprecation")
    private String arguments(CommandSender p, String arg) {
        if (p instanceof Player) {
            arg = arg.replace("%argument_", "").replace("%", "");
            String[] args = Main.getInstance().getPlayerInfo().getArguments((Player) p);
            if (args != null)
                try {
                    // RETURN PLACEHOLDER
                    String[] list = arg.split("_");
                    if (!(list.length > 2))
                        throw new ArrayIndexOutOfBoundsException();
                    int pos = val(list[0].trim());
                    List<String> phlist = new ArrayList<>();
                    for (String str : list)
                        if (!str.equals(list[0]))
                            phlist.add(str);
                    String ph = "%";
                    for (String str : phlist)
                        if (phlist.indexOf(str) + 1 != phlist.size())
                            ph = ph + str + "_";
                        else
                            ph = ph + str;
                    if (args.length >= pos) {
                        ArgumentType type = Main.getInstance().getPlayerInfo().getArgumentTypes((Player) p).get(pos);
                        if (type.equals(ArgumentType.PLAYER))
                            arg = getPh(Bukkit.getPlayer(args[pos]), ph + "%");
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    try {
                        // RETURN STRING
                        int pos = val(arg.split("_")[0].trim());
                        if (args.length >= pos + 1)
                            if (Main.getInstance().getPlayerInfo().getArgumentTypes((Player) p).size() - 1 >= pos) {
                                ArgumentType type = Main.getInstance().getPlayerInfo().getArgumentTypes((Player) p)
                                        .get(pos);
                                if (type.equals(ArgumentType.PLAYER))
                                    arg = Bukkit.getPlayer(args[pos]).getName();
                                else if (type.equals(ArgumentType.STRING) || type.equals(ArgumentType.INTEGER))
                                    arg = args[pos];
                                else if (type.equals(ArgumentType.OFFLINE))
                                    arg = Bukkit.getOfflinePlayer(args[pos]).getName();
                            } else
                                arg = args[pos];
                        else
                            arg = "";
                    } catch (ArrayIndexOutOfBoundsException e2) {
                        // Terrible argument catch, or extra arguments not available
                        arg = "";
                    } catch (NumberFormatException e4) {
                        String pos = arg.split("_")[0];
                        if (pos.contains("-") || pos.contains("+"))
                            try {
                                // FROMID - TOID
                                int from = val(pos.split("-")[0].trim());
                                int to = val(pos.split("-")[1].trim());
                                String newStr = null;
                                for (int i = from; i < to; i++)
                                    if (args.length >= i + 1)
                                        if (newStr == null)
                                            newStr = args[i];
                                        else
                                            newStr = newStr + " " + args[i];
                                return newStr;
                            } catch (NumberFormatException e2) {
                                //FROM - ALL
                                int from = val(pos.replace("+", "").trim());
                                String newStr = "";
                                if (args.length >= from - 1)
                                    for (int i = from; i < args.length; i++)
                                        if (newStr.equals(""))
                                            newStr = args[i];
                                        else
                                            newStr = newStr + " " + args[i];
                                return newStr;
                            }
                    }
                } catch (NumberFormatException e) {
                    return arg;
                }
        }
        return arg;
    }

    private static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ')
                    nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length())
                    throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            // | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+'))
                        x += parseTerm(); // addition
                    else if (eat('-'))
                        x -= parseTerm(); // subtraction
                    else
                        return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*'))
                        x *= parseFactor(); // multiplication
                    else if (eat('/'))
                        x /= parseFactor(); // division
                    else
                        return x;
                }
            }

            double parseFactor() {
                if (eat('+'))
                    return parseFactor(); // unary plus
                if (eat('-'))
                    return -parseFactor(); // unary minus
                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.')
                        nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z')
                        nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    switch (func) {
                        case "sqrt":
                            x = Math.sqrt(x);
                        case "sin":
                            x = Math.sin(Math.toRadians(x));
                        case "cos":
                            x = Math.cos(Math.toRadians(x));
                        case "tan":
                            x = Math.tan(Math.toRadians(x));
                            break;
                        default:
                            throw new RuntimeException("Unknown function: " + func);
                    }
                } else
                    throw new RuntimeException("Unexpected: " + (char) ch);
                if (eat('^'))
                    x = Math.pow(x, parseFactor()); // exponentiation
                return x;
            }
        }.parse();
    }

    public String colorPre(CommandSender p, String str) {
        return color(p, str.replaceAll("%prefix%", getPrefix()));
    }

    private Integer val(String str) throws NumberFormatException {
        return Integer.valueOf(str);
    }
}
