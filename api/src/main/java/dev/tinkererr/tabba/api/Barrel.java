package dev.tinkererr.tabba.api;

import org.bukkit.Material;

import java.math.BigInteger;

/**
 * This class represents barrels which hold a large quantity of one item type.
 */
public class Barrel {

    private Material material;
    private BigInteger amount;
    private BarrelTier tier;

    /**
     * Instantiates a new empty barrel with the default barrel tier.
     */
    public Barrel() {
        this(null, BigInteger.ZERO, BarrelTier.getDefault());
    }

    /**
     * Instantiates a new barrel.
     *
     * @param material The material of the item stored in the barrel.
     * @param amount   The amount of the item currently stored in the barrel.
     * @param tier     The barrel's {@link BarrelTier}.
     */
    public Barrel(Material material, BigInteger amount, BarrelTier tier) {
        this.material = material;
        this.amount = amount;
        this.tier = tier;
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
        if(space.signum() != 1) {
            return amount;
        } else {
            this.amount = this.amount.add(amount);
            BigInteger temp = this.amount.min(this.tier.getCapacity());
            BigInteger result = this.amount.subtract(temp);
            this.amount = temp;
            return result;
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
}
