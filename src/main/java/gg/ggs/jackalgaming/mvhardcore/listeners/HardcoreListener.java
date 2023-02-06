package gg.ggs.jackalgaming.mvhardcore.listeners;

import java.util.logging.Level;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.WorldConfigSection;

public class HardcoreListener implements Listener {
    private final MultiverseHardcore plugin;

    public HardcoreListener(MultiverseHardcore plugin) {
        this.plugin = plugin;
    }

    /**
     * Called when a player teleports.
     *
     * @param event The player teleport event.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void playerTeleport(PlayerTeleportEvent event) {
        if (event.isCancelled() || event.getFrom().getWorld().equals(event.getTo().getWorld())) {
            return;
        }

        World world = event.getTo().getWorld();
        Player player = event.getPlayer();
        
        boolean isPlayerPresencePermitted = this.plugin.getPlayerService().isPlayerPresencePermitted(player, world);
        if (!isPlayerPresencePermitted) {
            event.setCancelled(true);
        }
    }
}
