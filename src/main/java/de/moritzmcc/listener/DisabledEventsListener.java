package de.moritzmcc.listener;

import de.moritzmcc.command.BuildCommand;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class DisabledEventsListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getEntity().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getWhoClicked().getUniqueId())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent event) {
        for (HumanEntity humanEntity: event.getInitiator().getViewers()) {
            if (BuildCommand.playersInBuildMode.contains(humanEntity.getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(BuildCommand.playersInBuildMode.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
        }
    }
}
