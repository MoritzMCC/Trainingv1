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
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomInventoryDamager {



    public static void setRandomInventory(Player player) {

        int inventoryItemsType = ThreadLocalRandom.current().nextInt(1, 20);
        Random random = new Random();
        if (inventoryItemsType < 3) { //recraft mushroom
            int amount = ThreadLocalRandom.current().nextInt(8, 64);
            player.getInventory().setItem(13, new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, amount));
            player.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, amount));
            return;
        }
        if (inventoryItemsType > 2 && inventoryItemsType < 6) { //recraft mushroom
            int amount = random.nextInt(8, 64);
            player.getInventory().setItem(13, new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, amount));
            player.getInventory().setItem(15, new ItemStack(Material.BROWN_MUSHROOM, amount));
            return;
        }
        if (inventoryItemsType > 5 && inventoryItemsType < 15) {

            int amount = random.nextInt(4) + 6;
            int rotations = amount;
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < rotations; i++) {
                int position = random.nextInt(9, 36);
                if (!positions.contains(position)) {
                    positions.add(position);
                } else rotations++;

            }

            for (int i = 0; i < amount; i++) {
                player.getInventory().setItem(positions.get(i), new ItemStack(Material.MUSHROOM_STEW));
            }


        }


        if (inventoryItemsType > 14 && inventoryItemsType < 18) {
            int amount = random.nextInt(8, 64);
            player.getInventory().setItem(13, new ItemStack(Material.BOWL, amount));
            player.getInventory().setItem(14, new ItemStack(Material.COCOA_BEANS, amount));
        }

        if (inventoryItemsType > 17) {
            int amount = random.nextInt(6, 11);
            int rotations = 3;
            List<Integer> positions = new ArrayList<>();
            for (int i = 0; i < rotations; i++) {
                int position = random.nextInt(9, 37);
                if (!positions.contains(position)) {
                    positions.add(position);
                } else rotations++;

            }
            player.getInventory().setItem(positions.get(positions.get(0)), new ItemStack(Material.BOWL));
            player.getInventory().setItem(positions.get(positions.get(1)), new ItemStack(Material.RED_MUSHROOM));
            player.getInventory().setItem(positions.get(positions.get(2)), new ItemStack(Material.BROWN_MUSHROOM));


        }
    }

        public static void doInventoryFill(Player player, String damagerName){

            if ("randominventory".equalsIgnoreCase(DamagerConfigManager.getDamagerType(damagerName))) {
                player.getInventory().clear();
                player.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
                RandomInventoryDamager.setRandomInventory(player);
            }

        }




    }




