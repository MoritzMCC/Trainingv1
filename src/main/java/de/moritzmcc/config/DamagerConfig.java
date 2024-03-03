package de.moritzmcc.config;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class DamagerConfig {
    private final File file;
    private final YamlConfiguration config;

    public DamagerConfig() {

        File dir = new File("./plugins/damagerConfig");

        if (!dir.exists()){
            dir.mkdir();
        }

        this.file = new File(dir,"damagerConfig.yml");

        if (!file.exists()){
            try {
                file.createNewFile();
            }catch (IOException e){ e.printStackTrace();}
        }
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public File getFile() {
        return file;
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void save() {

        try {
            config.save(file);
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void add(String name, double damage, String chatcolor, Location location, int tickspeed, String damagerType) {
        if (!config.contains(name)) {
            config.set(String.format("%s.Damage", name), damage);
            config.set(String.format("%s.Chatcolor", name), chatcolor);
            config.set(String.format("%s.Location", name), location);
            config.set(String.format("%s.Tickspeed", name), tickspeed);
            config.set(String.format("%s.DamagerType", name), damagerType);


        }
        save();
    }

    public void edit(String name, double newDamage, String newChatcolor, int tickspeed) {
        String path = String.format("%s.Damage", name);
        config.set(path, newDamage);

        path = String.format("%s.Chatcolor", name);
        config.set(path, newChatcolor);

        path = String.format("%s.Tickspeed", name);
        config.set(path, tickspeed);

        save();
    }

    public void redirect(String name, Location location){
        String path = String.format("%s.Location", name);
        config.set(path, location);
        save();
    }

    public void delete(String name){
            if (config.contains(name)) {
                config.set(name, null);
                save();

        }
    }

    public Set<String> getKeys() {
        return config.getKeys(false);
    }

    public double getDamagerDamage(String name){
        double damage = config.getDouble(String.format("%s.Damage", name));
        return damage;
    }
    public ChatColor getDamagerColor(String name){
        String chatColorString = config.getString(String.format("%s.Chatcolor", name));
        ChatColor chatColor = ChatColor.valueOf(chatColorString);
        return chatColor;
    }
    public Location getDamagerLocation(String name){
        Location location = (Location) config.get(String.format("%s.Location", name));
        return location;
    }
    public int getDamagerTickspeed(String name){
        int tickspeed =  config.getInt(String.format("%s.Tickspeed", name));
        return tickspeed;
    }

    public String getDamagerType(String name){
        String damagertype = config.getString(String.format("%s.DamagerType", name));
        return damagertype;
    }

}

