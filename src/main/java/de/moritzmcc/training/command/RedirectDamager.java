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

import java.io.IOException;

public class RedirectDamager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp()) return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (strings.length != 1) {
            player.sendMessage(ChatColor.GREEN + "Please Use: " + ChatColor.AQUA + "/redirectDamager name");
            return false;
        }
        if (!DamagerConfigManager.containsDamager(strings[0])) {
            player.sendMessage(ChatColor.DARK_RED + "This damager does not exist. Use one of the following " + DamagerConfigManager.getAllDamagers());
            return false;
        }

        String name = strings[0];
        Location location = player.getLocation();

        try {
            DamagerConfigManager.redirectDamager(name, location);
            player.sendMessage(ChatColor.DARK_GREEN + name + " successfully moved");
            HologramUtils.reloadHologramms(player.getWorld());

        }catch (Exception e){
            player.sendMessage("An Error occurred: " + e);
        }

        return true;
    }
}
