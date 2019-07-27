package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.math.BigInteger;

/**
 * This listener handles all hopper interactions with {@link dev.tinkererr.tabba.api.Barrel}s.
 */
public class HopperListener implements Listener {

    private final TABBA plugin;

    /**
     * Instantiates the listener.
     *
     * @param plugin The plugin's main class instance.
     */
    public HopperListener(TABBA plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHopperTransfer(InventoryMoveItemEvent event) {
        if (event.getInitiator().getType() == InventoryType.HOPPER && event.getDestination().getType() == InventoryType.HOPPER
                && event.getItem().hasItemMeta() && event.getItem().getItemMeta().hasDisplayName()
                && event.getItem().getItemMeta().getDisplayName().equalsIgnoreCase("TABBA Hopper Check")) {
            event.setCancelled(true);
            this.plugin.getBarrelProvider().getBarrel(event.getInitiator().getLocation().add(0, 1, 0)).ifPresent(barrel -> {
                if (barrel.getMaterial() != null && barrel.getAmount().compareTo(BigInteger.ZERO) > 0) {
                    Material cached = barrel.getMaterial();
                    BigInteger amountToTake = barrel.takeItems(BigInteger.ONE);
                    this.plugin.getBarrelProvider().saveBarrel(barrel);
                    if (amountToTake.equals(BigInteger.ONE)) {
                        event.getDestination().addItem(new ItemStack(cached, 1));
                    }
                }
            });
        }
        if (event.getDestination().getType() == InventoryType.BARREL) {
            this.plugin.getBarrelProvider().getBarrel(event.getDestination().getLocation()).ifPresent(barrel -> {
                ItemStack item = event.getItem();
                if (item.hasItemMeta()) {
                    return;
                }
                if (barrel.getMaterial() == null) {
                    barrel.setMaterial(item.getType());
                }
                if (item.getType() == barrel.getMaterial()) {
                    BigInteger amountAdded = barrel.addItems(new BigInteger(String.valueOf(item.getAmount())));
                    this.plugin.getBarrelProvider().saveBarrel(barrel);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            item.setAmount(item.getAmount() - amountAdded.intValueExact());
                        }
                    }.runTaskLater(this.plugin, 1L);
                }
            });
        }
    }
}
