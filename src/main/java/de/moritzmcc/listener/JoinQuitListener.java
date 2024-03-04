package de.moritzmcc.listener;

import de.moritzmcc.teleporter.Teleporter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
        setSpawnItems(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    public static void setSpawnItems(Player player){
        player.getInventory().clear();
        player.getPlayer().getInventory().setItem(0, Teleporter.getTeleporterItem());
    }
}
