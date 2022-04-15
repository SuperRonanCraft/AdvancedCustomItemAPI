package me.SuperRonanCraft.AdvancedCustomItemAPI.player.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;
import me.SuperRonanCraft.AdvancedCustomItemAPI.references.web.Updater;

public class Join implements Listener {
	private boolean updater = false;

	Join() {
		Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (updater)
			updater(e.getPlayer());
	}

	private void updater(Player p) {
		Main pl = Main.getInstance();
		if (pl.getPerms().getUpdate(p))
			if (!pl.getDescription().getVersion().equals(Updater.updatedVersion) && Updater.updatedVersion != null)
				p.sendMessage(pl.getText().color(p,
						"&7There is currently an update for &6AdvancedCustomItemAPI &7version &e#" + Updater.updatedVersion
								+ " &7you have version &e#" + pl.getDescription().getVersion()));
	}

	public void setUpdater(boolean update) {
		updater = update;
	}
}
