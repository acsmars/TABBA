package dev.tinkererr.tabba.api;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Location;
import org.bukkit.Material;

import java.math.BigInteger;

/**
 * This class represents barrels which hold a large quantity of one item type.
 */
public abstract class Barrel {

    private Location location;
    private Material material;
    private BigInteger amount;
    private BarrelTier tier;

    /**
     * Instantiates a new empty barrel with the default barrel tier.
     *
     * @param location The location of the barrel.
     */
    public Barrel(Location location) {
        this(location, null, BigInteger.ZERO, BarrelTier.getDefault());
    }

    /**
     * Instantiates a new barrel.
     *
     * @param location The location of the barrel.
     * @param material The material of the item stored in the barrel.
     * @param amount   The amount of the item currently stored in the barrel.
     * @param tier     The barrel's {@link BarrelTier}.
     */
    public Barrel(Location location, Material material, BigInteger amount, BarrelTier tier) {
        this.location = location;
        this.material = material;
        this.amount = amount;
        this.tier = tier;
    }

    /**
     * Returns the location of this barrel in the world.
     *
     * @return The barrel's location.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Returns the material of the item currently stored in this barrel.
     *
     * @return The barrel's item material.
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Returns the amount of the item currently stored in this barrel.
     *
     * @return The barrel's item quantity.
     */
    public BigInteger getAmount() {
        return this.amount;
    }

    /**
     * Returns the tier of this barrel.
     *
     * @return The barrel's {@link BarrelTier}.
     */
    public BarrelTier getTier() {
        return this.tier;
    }

    /**
     * Checks if this barrel is currently full based on the {@link BarrelTier}'s maximum capacity.
     *
     * @return True if the barrel is currently full (or over-full), otherwise false.
     */
    public boolean isFull() {
        return this.amount.compareTo(this.tier.getCapacity()) >= 0;
    }

    /**
     * Adds a quantity to this barrel. This method will attempt to add as much of the specified amount as possible until
     * the barrel is full, and return any remainder that was unable to fit in the barrel.
     *
     * @param amount The amount of item to add to this barrel.
     * @return Any remainder left over from the addition if the barrel exceeded maximum capacity.
     */
    public BigInteger addItems(BigInteger amount) {
        if (amount.signum() != 1) {
            throw new IllegalArgumentException("Amount must be greater than 0!");
        }
        BigInteger maximum = this.tier.getCapacity();
        BigInteger space = maximum.subtract(this.amount);
        if (space.signum() != 1) {
            return amount;
        } else if(this.tier.getCapacity() == null) {
            this.amount = this.amount.add(amount);
            return BigInteger.ZERO;
        } else {
            this.amount = this.amount.add(amount);
            BigInteger temp = this.amount.min(this.tier.getCapacity());
            BigInteger result = this.amount.subtract(temp);
            this.amount = temp;
            return result;
        }
    }

    /**
     * Removes a quantity from this barrel. This method will attempt to remove as many of the specified amount as possible
     * until the barrel is empty, and return the amount that it managed to remove.
     *
     * @param amount The amount of items to take from this barrel.
     * @return The actual amount that was taken from this barrel (factoring in how many could be taken).
     */
    public BigInteger takeItems(BigInteger amount) {
        if (amount.signum() != 1) {
            throw new IllegalArgumentException("Amount must be greater than 0!");
        }
        if (amount.compareTo(this.amount) >= 0) {
            BigInteger temp = this.amount;
            this.amount = BigInteger.ZERO;
            return temp;
        } else {
            this.amount = this.amount.subtract(amount);
            return amount;
        }
    }

    /**
     * Upgrades this barrel to the next {@link BarrelTier} if possible.
     *
     * @return True if the barrel has been upgraded to the next tier, or false if the barrel is already the highest tier.
     */
    public boolean upgradeTier() {
        if (this.tier.getNextTier() == null) {
            return false;
        } else {
            this.tier = this.tier.getNextTier();
            return true;
        }
    }

    /**
     * Returns the HUD text that should be displayed when a player looks at this barrel.
     *
     * @return The barrel's HUD text.
     */
    public BaseComponent[] getHUDText() {
        return new ComponentBuilder("|| ")
                .append("Tier: ")
                .append(TextComponent.fromLegacyText(this.tier.getFormattedName()))
                .append(" || ")
                .append("Item: ")
                .append(this.amount.equals(BigInteger.ZERO) ? "" : this.amount + "x ")
                .append(this.material == null ? "None" :
                        WordUtils.capitalize(this.material.name().toLowerCase().replaceAll("_", " ")))
                .append(" || ")
                .append("Capacity: ")
                .append(this.tier.getCapacity() == null ? "Unlimited" : this.tier.getCapacity().toString())
                .append(" ||")
                .create();
    }

}
