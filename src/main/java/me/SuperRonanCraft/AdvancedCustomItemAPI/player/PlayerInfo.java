package me.SuperRonanCraft.AdvancedCustomItemAPI.player;

import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import me.SuperRonanCraft.AdvancedCustomItemAPI.player.enums.ArgumentType;

public class PlayerInfo {

	HashMap<Player, String[]> arguments = new HashMap<Player, String[]>();
	HashMap<Player, List<ArgumentType>> argumentTypes = new HashMap<Player, List<ArgumentType>>();

	public void setArguments(Player p, String[] array) {
		arguments.put(p, array);
	}

	public void setArguments(Player p, List<ArgumentType> args) {
		argumentTypes.put(p, args);
	}

	public String[] getArguments(Player p) {
		return arguments.get(p);
	}

	public List<ArgumentType> getArgumentTypes(Player p) {
		return argumentTypes.get(p);
	}

	public void clear(Player p) {
		arguments.remove(p);
	}
}
