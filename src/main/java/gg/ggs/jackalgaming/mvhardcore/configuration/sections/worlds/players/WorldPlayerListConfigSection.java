package gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.players;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;

public class WorldPlayerListConfigSection extends ConfigSection {
    private final Map<String, WorldPlayerConfigSection> playerConfigMap;

    public WorldPlayerListConfigSection(ConfigFile configFile, String sectionName) {
        super(configFile, sectionName);
        this.playerConfigMap = new HashMap<>();
    }

    @Override
    public void loadSection() {        
        Set<String> playersFromConfig = getValueFromConfig();
        this.playerConfigMap.clear();
        for(String playerId : playersFromConfig) {
            //Note: Don't have the player name from this list, but its value shouldn't get overridden.
            addPlayerConfigSection(playerId, playerId);
        }
    }

    public Map<String, WorldPlayerConfigSection> getPlayerConfigMap() {
        return playerConfigMap;
    }

    public void addPlayerConfigSection(String playerId, String playerName) {
        WorldPlayerConfigSection newPlayerConfig = new WorldPlayerConfigSection(configFile, getPath(), playerId, playerName);

        //Add to local memory
        this.playerConfigMap.put(playerId, newPlayerConfig);

        //Write to file
        newPlayerConfig.loadSection();
    }
}
