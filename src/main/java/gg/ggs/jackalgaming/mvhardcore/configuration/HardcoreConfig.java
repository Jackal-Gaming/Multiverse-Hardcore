package gg.ggs.jackalgaming.mvhardcore.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigValue;
import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.WorldListConfigSection;

public class HardcoreConfig extends ConfigFile {
    public final ConfigValue<String> worldName;
    private final WorldListConfigSection worldListSection;
    private final MultiverseHardcore plugin;

    public HardcoreConfig(MultiverseHardcore plugin)
            throws IOException, SecurityException, IllegalArgumentException {
        super(plugin, "config.yml");

        this.plugin = plugin;
        this.worldName = new ConfigValue<>(this, "settings.world_name", "Hardcore");
        this.worldListSection = new WorldListConfigSection(this, "worlds");
    }

    @Override
    public void loadConfig() {
        // Properties
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(worldName);
        setDefaults(stringConfigValues);

        // Sections
        this.worldListSection.loadSection();

        this.save();
    }

    @Override
    public void log(Level level, String msg) {
        this.plugin.log(level, msg);
    }

    public String getWorldName() {
        return this.worldName.getValueFromConfig();
    }

    public WorldListConfigSection getWorldListSection() {
        return this.worldListSection;
    }
}
