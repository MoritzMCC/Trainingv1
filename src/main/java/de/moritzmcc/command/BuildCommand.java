package de.moritzmcc.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuildCommand implements CommandExecutor {

    public static List<UUID> playersInBuildMode = new ArrayList<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("Training.buildCommand"))return false;

        Player player = null;
        if (!(sender instanceof Player)){
            if (args.length != 1) {
                sender.sendMessage("you have to define a player you want to put in build mode");
            }
            player = getPlayerbyName(args[0]);
        }

        if (args.length > 1){
            sender.sendMessage("Please use /build or /build name");
            return false;
        }

        if (args.length == 1) player = getPlayerbyName(args[0]);
        else player = (Player) sender;


        if (playersInBuildMode.contains(player.getUniqueId())) {
            playersInBuildMode.remove(player.getUniqueId());
            sender.sendMessage("§cBuild mode disabled");
        } else {
            playersInBuildMode.add(player.getUniqueId());
            sender.sendMessage("§aBuild mode enabled");

        }

        return true;
    }

    private Player getPlayerbyName(String name){
       return Bukkit.getPlayer(name);
    }

    public static List<UUID> getPlayersInBuildMode() {
        return playersInBuildMode;
    }
}
