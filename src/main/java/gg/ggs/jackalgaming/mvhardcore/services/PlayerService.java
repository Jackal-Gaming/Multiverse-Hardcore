package gg.ggs.jackalgaming.mvhardcore.services;

import java.util.logging.Level;

import org.bukkit.World;
import org.bukkit.entity.Player;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.WorldConfigSection;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.players.WorldPlayerConfigSection;

public class PlayerService {
    private final MultiverseHardcore plugin;

    public PlayerService(MultiverseHardcore plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles a player's presence in a particular world
     * @param player
     * @param world
     * @return If the player is allowed to be in the given world
     */
    public boolean isPlayerPresencePermitted(Player player, World world) {
        if (world == null || player == null) {
            return false;
        }

        String toWorldName = world.getName();
        WorldConfigSection registeredWorld = this.plugin.getHardcoreConfig().getWorldListSection().getWorldConfigMap().getOrDefault(toWorldName, null);
        if (registeredWorld == null) {
            //Players will not be preventing in visiting unregistered (non-hardcore) worlds
            return true;
        }

        String playerId = player.getUniqueId().toString();
        
        WorldPlayerConfigSection activePlayer = registeredWorld.getPlayerListSection().getPlayerConfigMap().getOrDefault(playerId, null);
        if (activePlayer == null) {
            this.plugin.log(Level.INFO, "Adding player " + player.getName() + " to hardcore config for world " + toWorldName);
            registeredWorld.getPlayerListSection().addPlayerConfigSection(playerId, player.getName());
            this.plugin.getHardcoreConfig().save();
            //Players are always allowed to visit a hardcore world on their first visit
            return true;
        } else if (activePlayer.getDeaths() >= 1) {
            this.plugin.log(Level.INFO, "Preventing player from teleporting to world: " + toWorldName);
            //Player with 1 or more deaths in a hardcore world are not allowed to visit
            return false;
        }
        return true;
    }
}
