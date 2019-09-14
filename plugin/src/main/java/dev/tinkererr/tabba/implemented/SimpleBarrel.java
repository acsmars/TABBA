package dev.tinkererr.tabba.implemented;

import dev.tinkererr.tabba.api.Barrel;
import dev.tinkererr.tabba.api.BarrelTier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigInteger;

/**
 * A simple (and the only) implementation of the {@link Barrel} abstract class.
 */
public class SimpleBarrel extends Barrel {

    public SimpleBarrel(Location location) {
        super(location);
    }

    public SimpleBarrel(Location location, Material material, BigInteger amount, BarrelTier tier) {
        super(location, material, amount, tier);
    }

    public static ItemStack getHopperCheck() {
        ItemStack dirt = new ItemStack(Material.DIRT, 1);
        ItemMeta meta = dirt.getItemMeta();
        meta.setDisplayName("TABBA Hopper Check");
        dirt.setItemMeta(meta);
        return dirt;
    }
}
