package de.moritzmcc.teleporter;

import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.damager.Damager;
import de.moritzmcc.util.InventoryAPI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teleporter implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if (Objects.equals(event.getItem(), getTeleporterItem())) {
            event.getPlayer().openInventory(teleporterInventory());
        }

    }

    @EventHandler
    public void onInInventoryClick(InventoryClickEvent event){
        if (event.getCurrentItem().equals(getDamagerTeleportItem())){
            event.getWhoClicked().closeInventory();
            event.getWhoClicked().openInventory(damagerInventory());

        }
    }

    public static ItemStack getTeleporterItem(){
        ItemStack itemStack = new ItemStack(Material.COMPASS);


        return itemStack;
    }

    private static Inventory teleporterInventory(){
        Inventory inventory = InventoryAPI.createInventory(5*9 , false);
        InventoryAPI.addItem(inventory, getDamagerTeleportItem() ,13);

        return inventory;
    }
    private static Inventory damagerInventory(){
        Inventory inventory = InventoryAPI.createInventory(9 , false);

        List<String> damagersList = new ArrayList<>(DamagerConfigManager.getAllDamagers());

        for (int i = 0; i < damagersList.size(); i++) {
            String damager = damagersList.get(i);
            InventoryAPI.addItem(inventory, getCustomDamagerItem(damager), i);
        }

        return inventory;
    }

    private static ItemStack getDamagerTeleportItem(){
        ItemStack itemStack = new ItemStack(Material.BEDROCK);

        return itemStack;
    }
    private static ItemStack getCustomDamagerItem(String damagerName){
        ItemStack itemStack = new ItemStack(Material.WHITE_WOOL);
        ItemMeta itemMeta = itemStack.getItemMeta();

        itemMeta.setDisplayName(ChatColor.getByChar(DamagerConfigManager.getDamagerColor(damagerName).getChar()) + "" + damagerName);

        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }


}
