package me.SuperRonanCraft.AdvancedCustomItemAPI.references.web;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.Bukkit;

import me.SuperRonanCraft.AdvancedCustomItemAPI.Main;

public class Updater {
	public static String updatedVersion = null;
	// Change after first release!
	String id = null;

	public Updater() {
		check();
	}

	void check() {
		if (id != null)
			synchronized (this) {
				try {
					HttpURLConnection con = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php")
							.openConnection();
					con.setDoOutput(true);
					con.setRequestMethod("POST");
					con.getOutputStream().write(
							("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + id)
									.getBytes("UTF-8"));
					updatedVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
				} catch (Exception ex) {
					Bukkit.getConsoleSender()
							.sendMessage("Failed to check for a update on spigot. Are you connected to the internet?");
					updatedVersion = Main.getInstance().getDescription().getVersion();
				}
			}
	}
}
