package gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;

public class WorldListConfigSection extends ConfigSection {
    private final Map<String, WorldConfigSection> worldConfigMap;

    public WorldListConfigSection(ConfigFile configFile, String sectionName) {
        super(configFile, sectionName);
        this.worldConfigMap = new HashMap<>();
    }

    @Override
    public void loadSection() {        
        Set<String> worldsFromConfig = getValueFromConfig();
        this.worldConfigMap.clear();
        for(String worldName : worldsFromConfig) {
            addWorldConfigSection(worldName);
        }
    }

    public Map<String, WorldConfigSection> getWorldConfigMap() {
        return worldConfigMap;
    }

    public void addWorldConfigSection(String worldName) {
        WorldConfigSection newWorldConfig = new WorldConfigSection(configFile, getPath(), worldName);

        //Add to local memory
        this.worldConfigMap.put(worldName, newWorldConfig);

        //Write to file
        newWorldConfig.loadSection();
    }
}
