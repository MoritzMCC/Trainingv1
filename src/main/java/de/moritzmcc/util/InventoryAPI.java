package de.moritzmcc.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class InventoryAPI implements Listener {

    public static HashMap<Inventory, Boolean> inventoryHash = new HashMap<>();


    public static Inventory createInventory(int size, boolean canTakeItem){
       Inventory inventory =  Bukkit.createInventory(null, size );
        fillEmptySpace(inventory);
        inventoryHash.put(inventory, canTakeItem);
       return inventory;
    }

    public static void addItem(Inventory inventory,ItemStack itemStack, int position ){

        inventory.setItem(position , itemStack);
    }

    public static void removeItem( Inventory inventory, int position){
        inventory.setItem(position , createFillItem());
    }

    public static void removeAllItems(Inventory inventory){

        for(int i = 0; i< inventory.getSize(); i++){
                inventory.setItem(i , createFillItem());
            }
        }



    private static void fillEmptySpace(Inventory inventory){

      for(int i = 0; i< inventory.getSize(); i++){
         if (inventory.getItem(i) == null){
             inventory.setItem(i , createFillItem());
         }
        }
    }

    private static ItemStack createFillItem(){
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();


        if (inventoryHash.containsKey(inventory) && inventoryHash.get(inventory) == false) {
            event.setCancelled(true);
         }
        }



}


