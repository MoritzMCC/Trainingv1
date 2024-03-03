package de.moritzmcc.command;

import de.moritzmcc.config.DamagerConfigManager;
import de.moritzmcc.util.HologramUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class AddDamagerCommand implements CommandExecutor {

    List<String>damagerTypes = new ArrayList<>();

    public AddDamagerCommand(){
        damagerTypes.add("normal");
        damagerTypes.add("crab");
        damagerTypes.add("randominventory");

    }




    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (!commandSender.isOp())return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (strings.length < 4 || strings.length > 5 ){
            player.sendMessage(ChatColor.GREEN + "Please Use: " + ChatColor.AQUA + "/addDamager name damage color tickspeed (type)");
            return false;
        }
        if (DamagerConfigManager.containsDamager(strings[0])){
            player.sendMessage(ChatColor.DARK_RED + "This damager already exist you can use /redirectDamager name or /editDamager");
            return false;
        }

        String name = strings[0];
        Location location = player.getLocation();
        double damage =  Double.parseDouble(strings[1]);
        ChatColor chatColor = ChatColor.valueOf(strings[2].toUpperCase());
        int tickspeed = Integer.parseInt(strings[3]);
        String damagerType;
        if (strings.length == 5) {
            damagerType = strings[4].toLowerCase();
            if (!damagerTypes.contains(damagerType)) {
                player.sendMessage("Please use one of the following types: " + damagerTypes);
                return false;
            }
        } else {
            damagerType = "normal";
        }

        try {
            DamagerConfigManager.addDamager(name, damage, chatColor, location, tickspeed, damagerType);
            player.sendMessage(ChatColor.DARK_GREEN + name + " succecfully added");
            HologramUtils.reloadHologramms(player.getWorld());

        }catch (Exception e){
            player.sendMessage("An Error occurred: " + e);
        }

        return false;
    }


}
