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

public class EditDamagerCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!commandSender.isOp())return false;
        if (!(commandSender instanceof Player)) return false;
        Player player = (Player) commandSender;

        if (strings.length != 4){
            player.sendMessage(ChatColor.GREEN + "Please Use: " + ChatColor.AQUA + "/editDamager name damage color tickspeed");
            return false;
        }
        if (!DamagerConfigManager.containsDamager(strings[0])){
            player.sendMessage(ChatColor.DARK_RED + "This damager does not exist. Use one of the following " + DamagerConfigManager.getAllDamagers());
            return false;
        }

        String name = strings[0];
        double damage =  Double.parseDouble(strings[1]);
        ChatColor chatColor = ChatColor.valueOf(strings[2].toUpperCase());
        int tickspeed = Integer.parseInt(strings[3]);


        try {
            DamagerConfigManager.editDamager(name, damage, chatColor, tickspeed);
            player.sendMessage(ChatColor.DARK_GREEN + name + " succecfully edited");
            HologramUtils.reloadHologramms(player.getWorld());

        }catch (Exception e){
            player.sendMessage("An Error occurred: " + e);
        }

        return false;
    }
}
