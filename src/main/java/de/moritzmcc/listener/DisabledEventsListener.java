package de.moritzmcc.listener;

import de.moritzmcc.command.BuildCommand;
import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.util.Area;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisabledEventsListener implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        event.setCancelled(true);
    }


    @EventHandler
    public void onDrop(PlayerDropItemEvent event){
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            return;
        }
        if (!Area.isInDamagerArea(event.getPlayer())){
            event.setCancelled(true);
        }
    }




}
