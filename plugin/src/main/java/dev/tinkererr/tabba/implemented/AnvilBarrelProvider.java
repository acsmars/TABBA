package dev.tinkererr.tabba.implemented;

import dev.tinkererr.anvil.api.meta.BlockMeta;
import dev.tinkererr.tabba.TABBA;
import dev.tinkererr.tabba.api.Barrel;
import dev.tinkererr.tabba.api.BarrelProvider;
import dev.tinkererr.tabba.api.BarrelTier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Implementation of the {@link BarrelProvider} interface which uses the Anvil block metadata framework to save barrel
 * data.
 */
public class AnvilBarrelProvider implements BarrelProvider {

    private final TABBA plugin;

    public AnvilBarrelProvider(TABBA plugin) {
        this.plugin = plugin;
    }

    /**
     * Obtains the {@link Barrel} at a given location.
     *
     * @param location The location of the barrel to get.
     * @return The barrel, if found at the location specified.
     */
    @Override
    public Optional<Barrel> getBarrel(Location location) {
        BlockMeta meta = this.plugin.getAnvil().getMeta(this.plugin, location);
        if (meta.exists()) {
            FileConfiguration data = meta.getData();
            if (!data.contains("material") || !data.contains("amount") || !data.contains("tier")) {
                data.set("material", "");
                data.set("amount", "0");
                data.set("tier", BarrelTier.getDefault().getId());
                meta.save();
            }
            return Optional.of(getBarrel(location, meta.getData()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Saves a {@link Barrel}'s data.
     *
     * @param barrel The barrel to save.
     * @return True if the barrel's data is saved successfully, otherwise false.
     */
    @Override
    public boolean saveBarrel(Barrel barrel) {
        BlockMeta meta = this.plugin.getAnvil().getMeta(this.plugin, barrel.getLocation());
        FileConfiguration data = meta.getData();
        data.set("material", barrel.getMaterial() == null ? "" : barrel.getMaterial().name());
        data.set("amount", barrel.getAmount().toString());
        data.set("tier", barrel.getTier().getId());
        return meta.save();
    }

    private Barrel getBarrel(Location location, FileConfiguration data) {
        return new SimpleBarrel(location, Material.getMaterial(data.getString("material")),
                new BigInteger(data.getString("amount")), BarrelTier.fromId(data.getInt("tier")));
    }
}
