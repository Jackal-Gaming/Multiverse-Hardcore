package gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;
import gg.ggs.jackalgaming.configuration.ConfigValue;

public class WorldConfigSection extends ConfigSection {
    private final ConfigValue<Date> createdAt;
    private final ConfigValue<String> alias;

    public WorldConfigSection(ConfigFile configFile, String sectionPath, String worldName) {
        super(configFile, sectionPath, worldName);
        
        this.createdAt = new ConfigValue<>(configFile, this.getValuePath("created_at"), new Date());
        this.alias = new ConfigValue<>(configFile, this.getValuePath("alias"), worldName);
    }

    @Override
    public void loadSection() {
        List<ConfigValue<Date>> dateConfigValues = Arrays.asList(createdAt);
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(alias);
        configFile.setDefaults(dateConfigValues);
        configFile.setDefaults(stringConfigValues);
    }

    public Date getCreatedAt() {
        return createdAt.getValueFromConfig();
    }

    public String getAlias() {
        return alias.getValueFromConfig();
    }
}
