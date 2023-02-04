package gg.ggs.jackalgaming.configuration;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValue<T extends Object> {
    private String path;
    private T defaultValue;

    public ConfigValue(String path, T defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
    }

    public String getPath() {
        return path;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    protected T getValueFromConfig(FileConfiguration config) {
        try {
            Class<T> targetType = (Class<T>) defaultValue.getClass();
            Object configValue = config.get(path, defaultValue);
            return (targetType).cast(configValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
