package gg.ggs.jackalgaming.mvhardcore.listeners;

import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.onarandombox.MultiverseCore.event.MVConfigReloadEvent;
import com.onarandombox.MultiverseCore.event.MVRespawnEvent;
import com.onarandombox.MultiverseCore.event.MVTeleportEvent;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class HardcoreMultiverseCoreListener implements Listener {
    private final MultiverseHardcore plugin;

    public HardcoreMultiverseCoreListener(MultiverseHardcore plugin) {
        this.plugin = plugin;
    }

    /**
     * This method is called when Multiverse-Core wants to reload config files.
     *
     * @param event The Config Reload event.
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    @EventHandler
    public void configReloadEvent(MVConfigReloadEvent event)
            throws SecurityException, IllegalArgumentException, IOException {
        this.plugin.reloadConfig();
        event.addConfig("Multiverse-Hardcore - config.yml");
    }

    /**
     * Called when a {@link MVTeleportEvent} is fired.
     * 
     * @param event The event.
     */
    @EventHandler
    public void playerTeleport(MVTeleportEvent event) {
        if (event.isCancelled()) {
            return;
        }

        World toWorld = event.getDestination().getLocation(event.getTeleportee()).getWorld();
        if (toWorld.equals(event.getFrom().getWorld())) {
            return;
        }

        Player player = event.getTeleportee();
        
        boolean isPlayerPresencePermitted = this.plugin.getPlayerService().isPlayerPresencePermitted(player, toWorld);
        if (!isPlayerPresencePermitted) {
            event.setCancelled(true);
        }
    }

    /**
     * Called when a {@link MVRespawnEvent} is fired.
     * 
     * @param event The event.
     */
    @EventHandler
    public void playerRespawn(MVRespawnEvent event) {
        World toWorld = event.getPlayersRespawnLocation().getWorld();
        Player player = event.getPlayer();

        boolean isPlayerPresencePermitted = this.plugin.getPlayerService().isPlayerPresencePermitted(player, toWorld);
        if (!isPlayerPresencePermitted) {
            Location respawnRedirectLocation = this.plugin.getHardcoreConfig().getGeneralSettings().getRespawnLocation().getLocation(plugin);
            event.setRespawnLocation(respawnRedirectLocation);
        }
    }
}
