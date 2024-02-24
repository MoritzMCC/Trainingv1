package de.moritzmcc.training.config;

import org.bukkit.ChatColor;
import org.bukkit.Location;

import java.util.Set;

public class DamagerConfigManager {

    static DamagerConfig config = new DamagerConfig();
    public static void addDamager(String name, double damage, ChatColor chatColor, Location location, int tickspeed) {

        String configKey = name.toLowerCase();
        location.setY( location.getBlockY() + 3);

        config.add(configKey, damage, chatColor.name(),location, tickspeed);

    }

    public static void editDamager(String name, double damage, ChatColor chatColor, int tickspeed){
        String configKey = name.toLowerCase();
        config.edit(configKey, damage, chatColor.name(), tickspeed);
    }

    public static void redirectDamager(String name, Location location){
        String configKey = name.toLowerCase();
        location.setY( location.getBlockY() + 3);
        config.redirect(configKey, location);
    }

    public static void deleteDamager(String name){
        String configKey = name.toLowerCase();
        config.delete(name);

    }

    public static boolean containsDamager(String name){
       for (String key:  config.getKeys()){
           if (key.equalsIgnoreCase(name)) return true;
       }
       return false;
    }

    public static Set<String> getAllDamagers(){
        return config.getKeys();
    }

    public static double getDamagerDamage(String name){
        return config.getDamagerDamage(name);
    }
    public static ChatColor getDamagerColor(String name){

        return config.getDamagerColor(name);
    }
    public static Location getDamagerLocation(String name){
        return config.getDamagerLocation(name);
    }

    public static int getDamagerTickspeed(String name){
        return config.getDamagerTickspeed(name);
    }
}
