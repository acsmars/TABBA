package dev.tinkererr.tabba.implemented;

import dev.tinkererr.tabba.api.Barrel;
import dev.tinkererr.tabba.api.BarrelTier;
import org.bukkit.Location;
import org.bukkit.Material;

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
}
