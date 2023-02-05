package gg.ggs.jackalgaming.mvhardcore.configuration;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigValue;
import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class HardcoreConfig extends ConfigFile {
    public static final ConfigValue<Date> CREATED_AT = new ConfigValue<>("world.created_at", null);
    public static final ConfigValue<String> WORLD_NAME = new ConfigValue<>("world.name", "Hardcore");

    public HardcoreConfig(MultiverseHardcore plugin)
            throws IOException, SecurityException, IllegalArgumentException {
        super(plugin, "config.yml");
    }

    @Override
    public void loadConfig() {
        List<ConfigValue<Date>> dateConfigValues = Arrays.asList(CREATED_AT);
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(WORLD_NAME);
        setDefaults(dateConfigValues);
        setDefaults(stringConfigValues);
        this.save();
    }
}
