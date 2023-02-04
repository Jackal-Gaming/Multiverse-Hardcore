package gg.ggs.jackalgaming.configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class ConfigFile {
    private Logger logger;
    private File file;
    private YamlConfiguration config;

    public ConfigFile(JavaPlugin plugin, String fileName)
            throws IOException, SecurityException, IllegalArgumentException {
        this.logger = plugin.getLogger();
        this.file = createFile(plugin, fileName, logger);
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    private static File createFile(JavaPlugin plugin, String fileName, Logger logger)
            throws IOException, SecurityException {
        plugin.getDataFolder().mkdirs();
        File configFile = new File(plugin.getDataFolder(), fileName);
        try {
            logger.log(Level.FINE, "Loading config file");
            boolean createdNew = configFile.createNewFile();
            logger.log(Level.FINE, "New file created: {0}", createdNew);
            return configFile;
        } catch (IOException | SecurityException e) {
            plugin.getLogger().log(Level.FINE, "Failed to load config file: {0}", e.getMessage());
            throw e;
        }
    }

    protected <T> void setDefaults(List<ConfigValue<T>> configValues) {
        for (ConfigValue<T> configValue : configValues) {
            String configValuePath = configValue.getPath();
            Object configDefaultValue = configValue.getDefaultValue();

            // A value is not defined within the config
            if (config.get(configValuePath) == null) {
                if (configDefaultValue != null) {
                    // Set the default value
                    logger.log(Level.FINE, "Config: Defaulting {0} to {1}",
                            new Object[] { configValuePath, configDefaultValue });
                    config.set(configValue.getPath(), configDefaultValue);
                } else {
                    // Or create a section for the value
                    config.createSection(configValuePath);
                }
            }
        }
    }

    public abstract void loadConfig();

    public void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            logger.log(Level.WARNING, "Could not save file. Error: {0}", e.getMessage());
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
