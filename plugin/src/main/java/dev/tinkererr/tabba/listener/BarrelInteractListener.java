package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.math.BigInteger;

/**
 * This listener class handles when a player interacts with a {@link dev.tinkererr.tabba.api.Barrel}.
 */
public class BarrelInteractListener implements Listener {

    private final TABBA plugin;

    /**
     * Instantiates the listener.
     *
     * @param plugin The plugin's main class instance.
     */
    public BarrelInteractListener(TABBA plugin) {
        this.plugin = plugin;
    }

    /**
     * Invoked by the server when a player interacts with a block.
     *
     * @param event The player interact event.
     */
    @EventHandler(ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BARREL) {
            this.plugin.getBarrelProvider().getBarrel(event.getClickedBlock().getLocation()).ifPresent(barrel -> {
                boolean sneaking = event.getPlayer().isSneaking();
                switch (event.getAction()) {
                    case RIGHT_CLICK_BLOCK:
                        event.setUseInteractedBlock(Event.Result.DENY);
                        event.setUseItemInHand(Event.Result.DENY);

                        ItemStack heldItem = event.getPlayer().getInventory().getItemInMainHand();
                        if(heldItem.hasItemMeta()) {
                            return;
                        }
                        if(barrel.getMaterial() == null) {
                            barrel.setMaterial(heldItem.getType());
                        }
                        if(heldItem.getType() != Material.AIR && heldItem.getType() == barrel.getMaterial()) {
                            BigInteger amountToAdd = new BigInteger(sneaking ? String.valueOf(heldItem.getAmount()) : "1");
                            BigInteger amountAdded = barrel.addItems(amountToAdd);
                            this.plugin.getBarrelProvider().saveBarrel(barrel);
                            heldItem.setAmount(heldItem.getAmount() - amountAdded.intValueExact());
                        }
                        break;
                    case LEFT_CLICK_BLOCK:
                        if(!barrel.getAmount().equals(BigInteger.ZERO)) {
                            event.setUseInteractedBlock(Event.Result.DENY);
                            event.setUseItemInHand(Event.Result.DENY);
                            if(barrel.getMaterial() != null) {
                                Material cachedMaterial = barrel.getMaterial();
                                BigInteger amountToTake = new BigInteger(sneaking ? "64" : "1");
                                BigInteger amountTaken = barrel.takeItems(amountToTake);
                                this.plugin.getBarrelProvider().saveBarrel(barrel);
                                event.getPlayer().getInventory().addItem(new ItemStack(cachedMaterial,
                                        amountTaken.intValueExact()));
                            }
                        }
                        break;
                    default:
                        break;
                }
            });
        }
    }
}
