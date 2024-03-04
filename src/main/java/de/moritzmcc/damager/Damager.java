package de.moritzmcc.damager;


import de.moritzmcc.damager.spezialDamagers.RandomInventoryDamager;
import de.moritzmcc.training.Main;
import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.util.Area;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

import static de.moritzmcc.damager.spezialDamagers.CrapDamager.getRandomItemStack;

public class Damager implements Listener {

    public Damager() {
        startAllDamagers();
        random = new Random();
    }

    protected final Map<UUID, String> players = new HashMap<>();
    protected final Map<UUID, Boolean> succecfullMap = new HashMap<>();
    protected final Map<UUID, Long> timeStamps = new HashMap<>();
    protected BukkitTask task;
    private int crapTick =1;
    Random random;


@EventHandler
    public void onMove(PlayerMoveEvent event){
    if (event.getFrom().equals(event.getTo()))return;
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
    public void onComplete(Player player) {
    succecfullMap.put(player.getUniqueId(), true);
    player.sendMessage("You completed the " + players.get(player.getUniqueId()));

        if (DamagerConfigManager.getDamagerType(players.get(player.getUniqueId())).equals("crap")) {
            timeStamps.remove(player.getUniqueId());
        }
    }



    public void onEnter(Player player, String damagername) {
        player.sendMessage("You entered " + damagername);
        succecfullMap.put(player.getUniqueId(), false);
        players.put(player.getUniqueId(), damagername);
        PlayerInventory inventory = player.getInventory();
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        player.setNoDamageTicks(DamagerConfigManager.getDamagerTickspeed(damagername));
        inventory.clear();
        inventory.addItem(new ItemStack(Material.STONE_SWORD));
        int amountSoups;
        if (!DamagerConfigManager.getDamagerType(damagername).equals("randominventory")){
            amountSoups = 32;


            int startSlot = 13;

            inventory.setItem(startSlot+1, new ItemStack(Material.RED_MUSHROOM, 64) );
            inventory.setItem(startSlot+2, new ItemStack(Material.BROWN_MUSHROOM,64));

            inventory.setItem(startSlot, new ItemStack(Material.BOWL, 64));

        }else amountSoups = 8;
        for (int i = 0; i < amountSoups; i++) {
            inventory.addItem( new ItemStack(Material.MUSHROOM_STEW));
        }
        if (DamagerConfigManager.getDamagerType(players.get(player.getUniqueId())).equals("crap")){
            Random random = new Random();
            timeStamps.put(player.getUniqueId(), System.currentTimeMillis() + (random.nextInt(crapTick) + 1) * 1000L);

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

                    if (DamagerConfigManager.getDamagerType(players.get(player.getUniqueId())).equals("crap")){
                        if (System.currentTimeMillis() > timeStamps.get(uuid)) {
                            timeStamps.put(player.getUniqueId(), System.currentTimeMillis() + (random.nextInt(crapTick) + 1) * 1000L);
                            if (hasEmptySpace(player)) {
                                player.getInventory().addItem(getRandomItemStack());
                            }
                        }
                    }
                    player.damage(getDamage(players.get(player.getUniqueId())));
                }
            }
        };
    }

    public double getDamage(String damegerName) {
        return DamagerConfigManager.getDamagerDamage(damegerName);
    }


    public void start(String damagerName) {
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), getDamageRunnable(), DamagerConfigManager.getDamagerTickspeed(damagerName), DamagerConfigManager.getDamagerTickspeed(damagerName));
    }

    public void startAllDamagers(){
    for (String name: DamagerConfigManager.getAllDamagers()){
        start(name);
    }
    }

    public void stop(){
      task.cancel();
      this.timeStamps.clear();
    }


    public void onLeave(Player player) {
        player.sendMessage("You left " + players.get(player.getUniqueId()) );
        String damagerType = DamagerConfigManager.getDamagerType(players.get(player.getUniqueId()));
        players.remove(player.getUniqueId());
        succecfullMap.remove(player.getUniqueId());


        if (damagerType.equals("crap")){
            timeStamps.remove(player.getUniqueId());
        }
        player.setNoDamageTicks(10);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());

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
        player.setNoDamageTicks(10);
        player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
        if (DamagerConfigManager.getDamagerType(players.get(player.getUniqueId())).equals("crap")){
            timeStamps.remove(player.getUniqueId());
        }
    }

    public void restart(){
    stop();
    startAllDamagers();
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    public void onOpenInventory(PlayerInteractEvent event){
    if (event.getItem() == null) return;

        if (!event.getItem().getType().equals(Material.MUSHROOM_STEW))return;

        Player player = (Player) event.getPlayer();

        if (!players.containsKey(player.getUniqueId())) return;
        if (hasSoupsInHotbar(player))return;
        String damagerName = players.get(player.getUniqueId());
       RandomInventoryDamager.doInventoryFill(player, damagerName);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onDropSoup(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getType().equals(Material.MUSHROOM_STEW)) {
            if (!players.containsKey(event.getPlayer().getUniqueId())) return;
            if (hasSoupsInHotbar(event.getPlayer()))return;
            String damagerName = players.get(event.getPlayer().getUniqueId());
            RandomInventoryDamager.doInventoryFill(event.getPlayer(), damagerName);
        }
    }

    private boolean hasSoupsInHotbar(Player player){
        for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
            ItemStack item = player.getInventory().getItem(hotbarSlot);

            if (item != null && item.getType() == Material.MUSHROOM_STEW) {
                return true;
            }
        }
        return false;
    }

    private boolean hasEmptySpace(Player player) {
        for (int i = 0; i < player.getInventory().getSize(); i++) {
          if (player.getInventory().getItem(i) == null)return true;
        }
        return false;
    }

}

