package me.SuperRonanCraft.AdvancedCustomItemAPI.player.events;

public class Listener implements org.bukkit.event.Listener {
    private Join join;

    public void registerUpdater(boolean update) {
        if (update)
            if (join == null)
                join = new Join();
        if (join != null)
            join.setUpdater(update);
    }
}
