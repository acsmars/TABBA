package dev.tinkererr.tabba.api;

import java.math.BigInteger;

/**
 * Enum representing all of the different tiers that a {@link Barrel} can have.
 */
public enum BarrelTier {

    /**
     * The wood tier is the lowest tier {@link Barrel} and can store 4096 of one item.
     */
    WOOD(1, new BigInteger("4096")),
    /**
     * The iron tier is the second tier of {@link Barrel} and can store 16384 of one item (4x as many as
     * {@link BarrelTier#WOOD}).
     */
    IRON(2, new BigInteger("16384")),
    /**
     * The gold tier is the third tier of {@link Barrel} and can store 131072 of one item (8x as many as
     * {@link BarrelTier#IRON}).
     */
    GOLD(3, new BigInteger("131072")),
    /**
     * The diamond tier is the fourth tier of {@link Barrel} and can store 16777216 of one item (128x as many as
     * {@link BarrelTier#GOLD}).
     */
    DIAMOND(4, new BigInteger("16777216")),
    /**
     * The nether star tier is the last tier of {@link Barrel} and can store an unlimited amount of one item. The actual
     * maximum capacity is limited to the maximum size of a {@link java.math.BigInteger}.
     */
    NETHER_STAR(5, null);

    private final int id;
    private final BigInteger capacity;

    BarrelTier(int id, BigInteger capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    /**
     * Returns the unique identifier for this tier.
     *
     * @return The ID for this tier.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the maximum capacity that this tier can store. If this result is null then this tier can store an
     * unlimited amount of items.
     *
     * @return The capacity of this tier.
     */
    public BigInteger getCapacity() {
        return capacity;
    }

    /**
     * Returns a tier from a given identifier.
     *
     * @param id The ID of the tier to lookup.
     * @return The tier corresponding to the specified identifier if found, otherwise null.
     */
    public static BarrelTier fromId(int id) {
        for (BarrelTier tier : values()) {
            if (tier.getId() == id) {
                return tier;
            }
        }
        return null;
    }

    /**
     * Returns the default barrel tier that newly placed barrels start with.
     *
     * @return The default tier for new barrels.
     */
    public static BarrelTier getDefault() {
        return WOOD;
    }
}
