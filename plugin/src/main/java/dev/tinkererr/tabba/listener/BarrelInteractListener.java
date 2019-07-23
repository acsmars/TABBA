package dev.tinkererr.tabba.listener;

import dev.tinkererr.tabba.TABBA;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
        if(event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.BARREL) {
            this.plugin.getBarrelProvider().getBarrel(event.getClickedBlock().getLocation()).ifPresent(barrel -> {
                event.setUseInteractedBlock(Event.Result.DENY);
                event.setUseItemInHand(Event.Result.DENY);
                event.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR,
                        new ComponentBuilder("|| ")
                                .append("Tier: ")
                                .append(TextComponent.fromLegacyText(barrel.getTier().getFormattedName()))
                                .append(" || ")
                                .reset()
                                .append("Item: ")
                                .append(barrel.getAmount().equals(BigInteger.ZERO) ? "" : barrel.getAmount() + "x ")
                                .append(barrel.getMaterial() == null ? "None" :
                                        WordUtils.capitalize(barrel.getMaterial().name().toLowerCase().replaceAll("_", " ")))
                                .append(" || ")
                                .append("Capacity: ")
                                .append(barrel.getTier().getCapacity() == null ? "Unlimited" : barrel.getTier().getCapacity().toString())
                                .append(" ||")
                                .create());
            });
        }
    }
}
