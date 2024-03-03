package de.moritzmcc.damager.spezialDamagers;

import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.damager.Damager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RandomInventoryDamager {



    public static void setRandomInventory(Player player){

        int inventoryItemsType = ThreadLocalRandom.current().nextInt(1,20);

        if (inventoryItemsType < 3 ){ //recraft mushroom
            int amount = ThreadLocalRandom.current().nextInt(8,64);
            player.getInventory().setItem(13 , new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14 , new ItemStack(Material.RED_MUSHROOM, amount));
            player.getInventory().setItem(15 , new ItemStack(Material.BROWN_MUSHROOM, amount));
            return;
        }
        if (inventoryItemsType >2 && inventoryItemsType <6 ){ //recraft mushroom
            int amount = ThreadLocalRandom.current().nextInt(8,64);
            player.getInventory().setItem(13 , new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14 , new ItemStack(Material.RED_MUSHROOM, amount));
            player.getInventory().setItem(15 , new ItemStack(Material.BROWN_MUSHROOM, amount));
            return;
        }
        if (inventoryItemsType >5 && inventoryItemsType < 15){
            int amount = ThreadLocalRandom.current().nextInt(6,11);
            int rotations = amount;
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < rotations; i++){
                int position =  ThreadLocalRandom.current().nextInt(9,37);
                if (!positions.contains(position)) {
                    positions.add(position);
                }else rotations++;

            }

            for (int i=0; i<amount; i++){
                player.getInventory().setItem(positions.get(i) , new ItemStack(Material.MUSHROOM_STEW));
            }


        }



        if (inventoryItemsType >14 && inventoryItemsType < 18){
            int amount = ThreadLocalRandom.current().nextInt(8,64);
            player.getInventory().setItem(13 , new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14 , new ItemStack(Material.COCOA_BEANS, amount));
        }

        if (inventoryItemsType >17){
            int amount = ThreadLocalRandom.current().nextInt(6,11);
            int rotations = 3;
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < rotations; i++){
                int position =  ThreadLocalRandom.current().nextInt(9,37);
                if (!positions.contains(position)) {
                    positions.add(position);
                }else rotations++;

            }

            for (int i=0; i<amount; i++){
                player.getInventory().setItem(positions.get(i) , new ItemStack(Material.MUSHROOM_STEW));
            }


        }




    }



}
