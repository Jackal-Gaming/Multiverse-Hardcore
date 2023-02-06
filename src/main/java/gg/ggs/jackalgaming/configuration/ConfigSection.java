package gg.ggs.jackalgaming.configuration;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;

public abstract class ConfigSection extends ConfigValue<Set<String>> {

    private static final String PATH_DELIMITER = ".";

    protected ConfigSection(ConfigFile configFile, String... sectionNamesAndPath) {
        super(configFile, String.join(PATH_DELIMITER, sectionNamesAndPath), new HashSet<String>());
    }

    protected ConfigSection(ConfigFile configFile, ConfigSection parentSection, String... sectionNames) {
        this(configFile, (String[]) ArrayUtils.addAll(new String[]{parentSection.getPath()}, sectionNames));
    }

    public abstract void loadSection();

    public String getValuePath(String valueName) {
        return getPath() + PATH_DELIMITER + valueName;
    }

    @Override
    public Set<String> getValueFromConfig() {
        try {
            return this.configFile.getConfig().getConfigurationSection(getPath()).getKeys(false);
        } catch (Exception e) {
            return getDefaultValue();
        }
    }

    @Override
    public void saveValueToConfig(Set<String> value) {
        configFile.getConfig().createSection(getPath());
    }
}
