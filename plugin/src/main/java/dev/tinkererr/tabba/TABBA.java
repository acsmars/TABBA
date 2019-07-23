package dev.tinkererr.tabba;

import dev.tinkererr.anvil.api.meta.BlockMetaProvider;
import dev.tinkererr.tabba.api.BarrelProvider;
import dev.tinkererr.tabba.implemented.AnvilBarrelProvider;
import dev.tinkererr.tabba.listener.BarrelInteractListener;
import dev.tinkererr.tabba.listener.BarrelPlaceListener;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Represents the main plugin class for TABBA.
 */
public class TABBA extends JavaPlugin {

    private BlockMetaProvider blockMeta;
    private AnvilBarrelProvider barrelProvider;

    /**
     * Invoked by Spigot when the server starts.
     */
    @Override
    public void onEnable() {
        if (!setupAnvil()) {
            this.getLogger().severe("Anvil plugin is missing! TABBA will now disable...");
            this.getServer().getPluginManager().disablePlugin(this);
            return;
        }

        this.barrelProvider = new AnvilBarrelProvider(this);
        this.getServer().getServicesManager().register(BarrelProvider.class, this.barrelProvider, this,
                ServicePriority.Normal);

        this.getServer().getPluginManager().registerEvents(new BarrelPlaceListener(this), this);
        this.getServer().getPluginManager().registerEvents(new BarrelInteractListener(this), this);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : TABBA.this.getServer().getOnlinePlayers()) {
                    Block target = player.getTargetBlockExact(5);
                    if (target != null && target.getType() == Material.BARREL) {
                        TABBA.this.barrelProvider.getBarrel(target.getLocation()).ifPresent(barrel ->
                                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, barrel.getHUDText(player.isSneaking()))
                        );
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20L);
    }

    private boolean setupAnvil() {
        if (this.getServer().getPluginManager().getPlugin("Anvil") == null) {
            return false;
        }
        RegisteredServiceProvider<BlockMetaProvider> rsp = this.getServer().getServicesManager()
                .getRegistration(BlockMetaProvider.class);
        if (rsp == null) {
            return false;
        }
        this.blockMeta = rsp.getProvider();
        return true;
    }

    /**
     * Returns the class which provides block metadata from Anvil.
     *
     * @return Anvil's block metadata provider instance.
     */
    public BlockMetaProvider getAnvil() {
        return this.blockMeta;
    }

    /**
     * Returns the barrel provider instance which allows getting and saving barrels.
     *
     * @return The barrel provider instance.s
     */
    public AnvilBarrelProvider getBarrelProvider() {
        return this.barrelProvider;
    }
}
