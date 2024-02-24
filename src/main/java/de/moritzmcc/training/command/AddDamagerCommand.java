package de.moritzmcc.training.command;

import de.moritzmcc.training.Main;
import de.moritzmcc.training.config.DamagerConfigManager;
import de.moritzmcc.training.damager.Damager;
import de.moritzmcc.training.util.HologramUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddDamagerCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {


        if (!commandSender.isOp())return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (strings.length != 4){
            player.sendMessage(ChatColor.GREEN + "Please Use: " + ChatColor.AQUA + "/addDamager name damage color tickspeed");
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


        try {
            DamagerConfigManager.addDamager(name, damage, chatColor, location, tickspeed);
            player.sendMessage(ChatColor.DARK_GREEN + name + " succecfully added");
            HologramUtils.reloadHologramms(player.getWorld());

        }catch (Exception e){
            player.sendMessage("An Error occurred: " + e);
        }

        return false;
    }
}
