package de.moritzmcc.command;

import de.moritzmcc.util.HologramUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadHolograms implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("you have to be a player");
            return false;
        }
        if (commandSender.isOp()){
            HologramUtils.reloadHologramms(((Player) commandSender).getWorld());
        }
        return true;
    }
}
