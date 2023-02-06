package gg.ggs.jackalgaming.configuration;

public class ConfigValue<T extends Object> {
    private String path;
    private T defaultValue;
    protected ConfigFile configFile;

    public ConfigValue(ConfigFile configFile, String path, T defaultValue) {
        this.path = path;
        this.defaultValue = defaultValue;
        this.configFile = configFile;
    }

    public String getPath() {
        return path;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    public T getValueFromConfig() {
        try {
            Class<T> targetType = (Class<T>) defaultValue.getClass();
            Object configValue = configFile.getConfig().get(path, defaultValue);
            return (targetType).cast(configValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public void saveValueToConfig(T value) {
        configFile.getConfig().set(path, value);
    }
}
