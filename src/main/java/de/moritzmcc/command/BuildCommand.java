package de.moritzmcc.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BuildCommand implements CommandExecutor {
    public static boolean buildMode = false;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (buildMode) {
            buildMode = false;
            sender.sendMessage("§cBuild mode disabled");
        } else {
            buildMode = true;
            sender.sendMessage("§aBuild mode enabled");
        }

        return false;
    }
}
