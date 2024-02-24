package de.moritzmcc.util;

import de.moritzmcc.config.DamagerConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;

import java.util.List;

public class HologramUtils {

    protected static final ArmorStand[] holograms = new ArmorStand[3];
    public static ArmorStand spawnHologram(Location location, String text) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class);
        armorStand.setGravity(false);
        armorStand.setMarker(true);
        armorStand.setVisible(false);
        armorStand.setCustomName(text);
        armorStand.setCustomNameVisible(true);
        return armorStand;
    }

    private static void setHolograms(Location hologramOrigin , String name, String color, double damage, int tickSpeed ) {
        holograms[0] = spawnHologram(hologramOrigin,ChatColor.valueOf(color) + name + " Damager");
        holograms[1] = spawnHologram(hologramOrigin.clone().subtract(0, 0.25, 0), ChatColor.WHITE + "Damage: " + ChatColor.GOLD + damage / 2 + ChatColor.RED.toString() + " \u2764");
        holograms[2] = spawnHologram(hologramOrigin.clone().subtract(0, 0.5, 0), ChatColor.WHITE + "Tickrate: " + ChatColor.GOLD + tickSpeed);
        for (ArmorStand hologram : holograms) {
            hologram.getLocation().getChunk().setForceLoaded(true);
            hologram.setPersistent(false);
        }
    }

    public static void createHologramms(){

        for (String name: DamagerConfigManager.getAllDamagers()){
          Location location =  DamagerConfigManager.getDamagerLocation(name);
          double damage = DamagerConfigManager.getDamagerDamage(name);
          ChatColor chatColor = DamagerConfigManager.getDamagerColor(name);
          int tickspeed = DamagerConfigManager.getDamagerTickspeed(name);

          setHolograms(location, name, chatColor.name(), damage , tickspeed);
        }
    }

    public static void deleteHologramsByAttributes(World world) {
        List<ArmorStand> armorStands = (List<ArmorStand>) world.getEntitiesByClass(ArmorStand.class);

        for (ArmorStand armorStand : armorStands) {
            armorStand.remove();
        }
    }

    private static boolean matchesAttributes(ArmorStand hologram, String name, String color, double damage, int tickSpeed) {
        String hologramName = hologram.getCustomName();

        if (hologramName != null && hologramName.contains(name)) {
            ChatColor hologramColor = ChatColor.valueOf(color);

            if (hologramColor != null && hologramName.contains(hologramColor.toString())) {
                // Assuming the format is "<name> Damager" and "<Damage / 2> ❤" and "<Tickrate>"
                String[] parts = hologramName.split(" ");
                double hologramDamage = Double.parseDouble(parts[2]);
                int hologramTickSpeed = Integer.parseInt(parts[3]);

                return hologramDamage == damage / 2 && hologramTickSpeed == tickSpeed;
            }
        }

        return false;
    }

    public static void reloadHologramms(World world){

        deleteHologramsByAttributes(world);

        createHologramms();
    }
}
