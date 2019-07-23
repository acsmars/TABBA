package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
        if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BARREL) {
            this.plugin.getBarrelProvider().getBarrel(event.getClickedBlock().getLocation()).ifPresent(barrel -> {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
            });
        }
    }
}
