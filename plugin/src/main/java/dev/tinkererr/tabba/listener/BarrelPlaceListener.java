package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import dev.tinkererr.tabba.implemented.SimpleBarrel;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * This listener class handles when a player places a {@link dev.tinkererr.tabba.api.Barrel}.
 */
public class BarrelPlaceListener implements Listener {

    private final TABBA plugin;

    /**
     * Instantiates the listener.
     *
     * @param plugin The plugin's main class instance.
     */
    public BarrelPlaceListener(TABBA plugin) {
        this.plugin = plugin;
    }

    /**
     * Invoked by the server when a block is placed. If the block placed is a barrel then the associated metadata is
     * established.
     *
     * @param event The block place event.
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if(event.getBlock().getType() == Material.BARREL) {
            this.plugin.getBarrelProvider().saveBarrel(new SimpleBarrel(event.getBlock().getLocation()));
        }
    }
}
