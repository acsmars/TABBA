package dev.tinkererr.tabba.api;

import org.bukkit.Location;

import java.util.Optional;

/**
 * Interface responsible for obtaining barrels.
 */
public interface BarrelProvider {

    /**
     * Obtains the {@link Barrel} at a given location.
     *
     * @param location The location of the barrel to get.
     * @return The barrel, if found at the location specified.
     */
    Optional<Barrel> getBarrel(Location location);
}
