package gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.players;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;
import gg.ggs.jackalgaming.configuration.ConfigValue;

public class WorldPlayerConfigSection extends ConfigSection {
    private final ConfigValue<String> alias;
    private final ConfigValue<Integer> deaths;

    /**
     * @param configFile
     * @param sectionPath
     * @param playerId
     * @param playerName
     */
    public WorldPlayerConfigSection(ConfigFile configFile, String sectionPath, String playerId, String playerName) {
        super(configFile, sectionPath, playerId);

        this.alias = new ConfigValue<>(configFile, this.getValuePath("alias"), playerName);
        this.deaths = new ConfigValue<>(configFile, this.getValuePath("deaths"), 0);
    }

    @Override
    public void loadSection() {
        configFile.log(Level.INFO, "Loading Section: " + getPath());
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(alias);
        List<ConfigValue<Integer>> integerConfigValues = Arrays.asList(deaths);
        configFile.setDefaults(stringConfigValues);
        configFile.setDefaults(integerConfigValues);
    }

    public String getAlias() {
        return alias.getValueFromConfig();
    }

    public Integer getDeaths() {
        return deaths.getValueFromConfig();
    }
}
