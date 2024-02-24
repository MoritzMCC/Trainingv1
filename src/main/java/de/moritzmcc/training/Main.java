package de.moritzmcc.training;

import de.moritzmcc.training.command.*;
import de.moritzmcc.training.damager.Damager;
import de.moritzmcc.training.listener.MoveListener;
import de.moritzmcc.training.util.HologramUtils;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Plugin instance;




    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
     getCommand("addDamager").setExecutor( new AddDamagerCommand());
     getCommand("editDamager").setExecutor( new EditDamagerCommand());
     getCommand("redirectDamager").setExecutor( new RedirectDamager());
     getCommand("deleteDamager").setExecutor( new RemoveDamager());
     getCommand("reloadHolograms").setExecutor( new ReloadHolograms());


     getServer().getPluginManager().registerEvents(new Damager(), this);

     HologramUtils.createHologramms();

    }

    @Override
    public void onDisable() {

    }

    public static Plugin getInstance() {
        return instance;
    }
}
