package de.moritzmcc.training;

import de.moritzmcc.command.*;
import de.moritzmcc.damager.Damager;
import de.moritzmcc.damager.spezialDamagers.CrapDamager;
import de.moritzmcc.damager.spezialDamagers.RandomInventoryDamager;
import de.moritzmcc.listener.DisabledEventsListener;
import de.moritzmcc.listener.JoinQuitListener;
import de.moritzmcc.teleporter.Teleporter;
import de.moritzmcc.util.HologramUtils;
import de.moritzmcc.util.InventoryAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    PluginManager pluginManager = getServer().getPluginManager();
    private static Plugin instance;




    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        /* -- Register commands -- */
        getCommand("addDamager").setExecutor( new AddDamagerCommand());
        getCommand("editDamager").setExecutor( new EditDamagerCommand());
        getCommand("redirectDamager").setExecutor( new RedirectDamager());
        getCommand("deleteDamager").setExecutor( new RemoveDamager());
        getCommand("reloadHolograms").setExecutor( new ReloadHolograms());
        getCommand("build").setExecutor(new BuildCommand());

        /* -- Register listener -- */
        pluginManager.registerEvents(new Damager(), this);
        pluginManager.registerEvents(new JoinQuitListener(), this);
        pluginManager.registerEvents(new DisabledEventsListener(), this);
        pluginManager.registerEvents(new Teleporter(), this);
        pluginManager.registerEvents(new InventoryAPI(), this);



        HologramUtils.createHologramms();
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getInstance() {
        return instance;
    }
}
