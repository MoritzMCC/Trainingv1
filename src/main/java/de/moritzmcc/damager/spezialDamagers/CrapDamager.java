package de.moritzmcc.damager.spezialDamagers;

import de.moritzmcc.damager.Damager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CrapDamager{
    static Random random = new Random();
    private static final List<Material> crapMaterials =Arrays.asList(
            Material.WOODEN_PICKAXE, Material.DIRT, Material.WHEAT_SEEDS,
            Material.COBBLESTONE, Material.OAK_PLANKS, Material.WOODEN_SWORD,
            Material.STONE_SWORD, Material.DIORITE, Material.OAK_SAPLING,
            Material.OAK_BUTTON, Material.CRAFTING_TABLE, Material.WOODEN_HOE,
            Material.ANVIL, Material.ARROW, Material.SAND, Material.COAL,
            Material.STICK, Material.POPPY, Material.SUNFLOWER);


    public static ItemStack getRandomItemStack() {
        Material material = crapMaterials.get(random.nextInt(crapMaterials.size()));
        return new ItemStack(material, random.nextInt(material.getMaxStackSize()) + 1);
    }




}
