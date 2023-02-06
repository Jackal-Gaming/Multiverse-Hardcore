package gg.ggs.jackalgaming.mvhardcore.listeners;

import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

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
            player.sendMessage(ChatColor.RED + "You can not teleport to the hardcore world you've died in: " + world.getName());
        }
    }

    /**
     * Called when a player joins the server.
     *
     * @param event The player join event.
     */
    @EventHandler
    public void playerJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = event.getPlayer().getLocation().getWorld();

        boolean isPlayerPresencePermitted = this.plugin.getPlayerService().isPlayerPresencePermitted(player, world);
        if (!isPlayerPresencePermitted) {
            Location respawnRedirectLocation = this.plugin.getHardcoreConfig().getGeneralSettings().getRespawnLocation().getLocation(plugin);
            player.teleport(respawnRedirectLocation);
            player.sendMessage(ChatColor.RED + "You have already died in the hardcore world: " + world.getName());
        }
    }
}
