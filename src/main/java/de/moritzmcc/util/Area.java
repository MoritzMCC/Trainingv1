package de.moritzmcc.util;

import de.moritzmcc.config.DamagerConfigManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Area {

  public static boolean isInDamagerArea(Player player){

      for (String name: DamagerConfigManager.getAllDamagers()){
          if (isInArea(player, name))return true;
      }
      return false;
  }

    private static boolean isInArea(Player player, String name) {
         int AREA_RADIUS = 2;

        Location playerLocation = player.getLocation();
        Location damagerLocation = DamagerConfigManager.getDamagerLocation(name);
        double referenceX = damagerLocation.getBlockX();
        double referenceZ = damagerLocation.getBlockZ();

        double playerX = playerLocation.getBlockX();
        double playerZ = playerLocation.getBlockZ();

        double minX = referenceX - AREA_RADIUS;
        double maxX = referenceX + AREA_RADIUS;
        double minZ = referenceZ - AREA_RADIUS;
        double maxZ = referenceZ + AREA_RADIUS;

        return playerX >= minX && playerX <= maxX && playerZ >= minZ && playerZ <= maxZ;
    }

  public static String getCurrentDamagerName(Player player){
      for (String name: DamagerConfigManager.getAllDamagers()){
          if (isInArea(player, name)){
              return name;
          }
      }
      return "easy";
  }


}
