package gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;
import gg.ggs.jackalgaming.configuration.ConfigValue;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.players.WorldPlayerListConfigSection;

public class WorldConfigSection extends ConfigSection {
    private final ConfigValue<Date> createdAt;
    private final ConfigValue<String> alias;
    private final WorldPlayerListConfigSection playerListConfigSection;

    public WorldConfigSection(ConfigFile configFile, String sectionPath, String worldName) {
        super(configFile, sectionPath, worldName);
        
        this.createdAt = new ConfigValue<>(configFile, this.getValuePath("created_at"), new Date());
        this.alias = new ConfigValue<>(configFile, this.getValuePath("alias"), worldName);
        this.playerListConfigSection = new WorldPlayerListConfigSection(configFile, this.getValuePath("players"));
    }

    @Override
    public void loadSection() {
        // Properties
        List<ConfigValue<Date>> dateConfigValues = Arrays.asList(createdAt);
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(alias);
        configFile.setDefaults(dateConfigValues);
        configFile.setDefaults(stringConfigValues);

        // Sections
        this.playerListConfigSection.loadSection();
    }

    public Date getCreatedAt() {
        return createdAt.getValueFromConfig();
    }

    public String getAlias() {
        return alias.getValueFromConfig();
    }

    public WorldPlayerListConfigSection getPlayerListSection() {
        return this.playerListConfigSection;
    }
}
