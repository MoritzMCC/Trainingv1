package de.moritzmcc.damager;


import de.moritzmcc.training.Main;
import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.util.Area;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class Damager implements Listener {

    public Damager() {
        startAllDamagers();
    }

    protected final Map<UUID, String> players = new HashMap<>();
    protected final Map<UUID, Boolean> succecfullMap = new HashMap<>();
    protected BukkitTask task;


@EventHandler
    public void onMove(PlayerMoveEvent event){
    if (event.getFrom() == event.getTo())return;
    Player player = event.getPlayer();

    if (!Area.isInDamagerArea(player)) {
        if (players.containsKey(player.getUniqueId())){
            player.getInventory().clear();
            onLeave(player);

        }
    }else {
        String damagername = Area.getCurrentDamagerName(player);
        if (players.containsKey(player.getUniqueId())) return;
        players.put(player.getUniqueId(), damagername);
        onEnter(player, damagername);
    }
}

    public void onEnter(Player player, String damagername) {
        player.sendMessage("You entered " + damagername);
        succecfullMap.put(player.getUniqueId(), false);
        PlayerInventory inventory = player.getInventory();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        inventory.clear();
        inventory.addItem(new ItemStack(Material.STONE_SWORD));

            int startSlot = 13;

            inventory.setItem(startSlot+1, new ItemStack(Material.RED_MUSHROOM, 64) );
            inventory.setItem(startSlot+2, new ItemStack(Material.BROWN_MUSHROOM,64));

            inventory.setItem(startSlot, new ItemStack(Material.BOWL, 64));

        for (int i = 0; i < 32; i++) {
            inventory.addItem(new ItemStack(Material.MUSHROOM_STEW));
        }


    }

    public Runnable getDamageRunnable() {
        return () -> {
            if (task.isCancelled()) {
                return;
            }
            List<UUID> uuids = new ArrayList<>(succecfullMap.keySet());
            for (UUID uuid : uuids) {
                if (succecfullMap.get(uuid)) {
                    continue;
                }
                Player player = (Player) Bukkit.getEntity(uuid);
                if (player != null) {
                    player.damage(getDamage(players.get(player.getUniqueId())));
                }
            }
        };
    }

    public double getDamage(String damegerName) {
        return DamagerConfigManager.getDamagerDamage(damegerName);
    }


    public void start(String damagerName) {
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), getDamageRunnable(), 0, DamagerConfigManager.getDamagerTickspeed(damagerName));
    }

    public void startAllDamagers(){
    for (String name: DamagerConfigManager.getAllDamagers()){
        start(name);
    }
    }

    public void stop(){
      task.cancel();
    }


    public void onLeave(Player player) {
        player.sendMessage("You left " + players.get(player.getUniqueId()) );

        players.remove(player.getUniqueId());
        succecfullMap.remove(player.getUniqueId());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        event.getDrops().clear();

        player.closeInventory();
        if (players.containsKey(player.getUniqueId())) {
            onFailure(player);

        }
    }

    public void onFailure(Player player) {

        players.remove(player.getUniqueId());
        player.sendMessage(ChatColor.RED + "You failed " + players.get(player.getUniqueId()));
        succecfullMap.remove(player.getUniqueId());


    }

    public void restart(){
    stop();
    startAllDamagers();
    }
}