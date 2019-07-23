package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Barrel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * This listener class handles when a player breaks with a {@link dev.tinkererr.tabba.api.Barrel}.
 */
public class BarrelBreakListener implements Listener {

    private final TABBA plugin;

    /**
     * Instantiates the listener.
     *
     * @param plugin The plugin's main class instance.
     */
    public BarrelBreakListener(TABBA plugin) {
        this.plugin = plugin;
    }

    /**
     * Invoked by the server when a block is broken.
     *
     * @param event The block break event.
     */
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.getBlock().getType() == Material.BARREL) {
            Barrel b = (Barrel) event.getBlock().getState();
            Inventory inventory = b.getInventory();

            ItemStack dirt = new ItemStack(Material.DIRT, 1);
            ItemMeta meta = dirt.getItemMeta();
            meta.setDisplayName("TABBA Hopper Check");
            dirt.setItemMeta(meta);

            if(inventory.contains(dirt)) {
                event.setCancelled(true);
                event.setDropItems(false);
                inventory.clear();
                event.getBlock().setType(Material.AIR);
                if(event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                    event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), new ItemStack(Material.BARREL));
                }
            }
        }
    }
}
