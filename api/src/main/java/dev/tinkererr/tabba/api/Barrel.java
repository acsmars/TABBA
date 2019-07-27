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
     * Sets the material of the item stored in the barrel.
     *
     * @param material The barrel item's material.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Adds a quantity to this barrel. This method will attempt to add as much of the specified amount as possible until
     * the barrel is full, and return how many items were actually added.
     *
     * @param amount The amount of item to add to this barrel.
     * @return The actual number of items that were added (factoring barrel capacity).
     */
    public BigInteger addItems(BigInteger amount) {
        if (amount.signum() != 1) {
            throw new IllegalArgumentException("Amount must be greater than 0!");
        }
        BigInteger maximum = this.tier.getCapacity();
        if (maximum == null) {
            this.amount = this.amount.add(amount);
            return amount;
        }
        if(this.amount.add(amount).compareTo(this.tier.getCapacity()) < 0) {
            this.amount = this.amount.add(amount);
            return amount;
        }
        BigInteger temp = this.amount.add(amount);
        BigInteger added = amount.subtract(temp.subtract(this.tier.getCapacity()).abs());
        this.amount = temp.min(this.tier.getCapacity());
        return amount;
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
        BigInteger result;
        if (amount.compareTo(this.amount) >= 0) {
            BigInteger temp = this.amount;
            this.amount = BigInteger.ZERO;
            result = temp;
        } else {
            this.amount = this.amount.subtract(amount);
            result = amount;
        }
        if (this.amount.equals(BigInteger.ZERO)) {
            this.material = null;
        }
        return result;
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
    public BaseComponent[] getHUDText(boolean detailed) {
        return new ComponentBuilder("|| ")
                .append("Tier: ")
                .append(TextComponent.fromLegacyText(this.tier.getFormattedName()))
                .append(" || ")
                .append("Item: ")
                .append(detailed ? (this.amount.equals(BigInteger.ZERO) ? "" : this.amount.toString() + " ")
                        : (this.amount.equals(BigInteger.ZERO) ? "" : this.amount.divide(new BigInteger("64")) + "x64" +
                        (this.amount.mod(new BigInteger("64")).equals(BigInteger.ZERO) ? " " :
                                "+" + this.amount.mod(new BigInteger("64")) + " ")))
                .append(this.material == null ? "None" :
                        WordUtils.capitalize(this.material.name().toLowerCase().replaceAll("_", " ")))
                .append(" || ")
                .append("Capacity: ")
                .append(this.tier.getCapacity() == null ? "Unlimited" : (detailed ? this.tier.getCapacity().toString()
                        : this.tier.getCapacity().divide(new BigInteger("64")) + "x64"))
                .append(" ||")
                .create();
    }

}
