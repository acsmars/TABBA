package dev.tinkererr.tabba.api;

import net.md_5.bungee.api.ChatColor;

import java.math.BigInteger;
import java.util.function.Predicate;

/**
 * Enum representing all of the different tiers that a {@link Barrel} can have.
 */
public enum BarrelTier {

    /**
     * The nether star tier is the last tier of {@link Barrel} and can store an unlimited amount of one item. The actual
     * maximum capacity is limited to the maximum size of a {@link java.math.BigInteger}.
     */
    NETHER_STAR(5, "&cNether Star", null, null, x -> true),
    /**
     * The diamond tier is the fourth tier of {@link Barrel} and can store 16777216 of one item (128x as many as
     * {@link BarrelTier#GOLD}).
     */
    DIAMOND(4, "&bDiamond", new BigInteger("16777216"), NETHER_STAR, x -> x.compareTo(new BigInteger("16777216")) < 0),
    /**
     * The gold tier is the third tier of {@link Barrel} and can store 131072 of one item (8x as many as
     * {@link BarrelTier#IRON}).
     */
    GOLD(3, "&6Golden", new BigInteger("131072"), DIAMOND, x -> x.compareTo(new BigInteger("131072")) < 0),
    /**
     * The iron tier is the second tier of {@link Barrel} and can store 16384 of one item (4x as many as
     * {@link BarrelTier#WOOD}).
     */
    IRON(2, "&7Iron", new BigInteger("16384"), GOLD, x -> x.compareTo(new BigInteger("16384")) < 0),
    /**
     * The wood tier is the lowest tier {@link Barrel} and can store 4096 of one item.
     */
    WOOD(1, "&6Wooden", new BigInteger("4096"), IRON, x -> x.compareTo(new BigInteger("4096")) < 0);

    private final int id;
    private final String formattedName;
    private final BigInteger capacity;
    private final BarrelTier nextTier;
    private final Predicate<BigInteger> fullFunction;

    BarrelTier(int id, String formattedName, BigInteger capacity, BarrelTier nextTier, Predicate<BigInteger> fullFunction) {
        this.id = id;
        this.formattedName = formattedName;
        this.capacity = capacity;
        this.nextTier = nextTier;
        this.fullFunction = fullFunction;
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
     * Returns the name of this tier with formatted color codes.
     *
     * @return The formatted name for this tier.
     */
    public String getFormattedName() {
        return ChatColor.translateAlternateColorCodes('&', this.formattedName) + ChatColor.RESET;
    }

    /**
     * Returns the maximum capacity that this tier can store. If this result is null then this tier can store an
     * unlimited amount of items.
     *
     * @return The capacity of this tier.
     */
    public BigInteger getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the tier that this tier can be upgraded to. If this is null then this tier cannot be upgraded further.
     *
     * @return The tier this tier will upgrade to.
     */
    public BarrelTier getNextTier() {
        return this.nextTier;
    }

    /**
     * Checks if this tier's capacity is greater than a provided quantity.
     *
     * @param amount The amount to check against the tier's capacity.
     * @return True if the provided amount is less than the tier's capacity, otherwise false.
     */
    public boolean hasSpace(BigInteger amount) {
        return this.fullFunction.test(amount);
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
