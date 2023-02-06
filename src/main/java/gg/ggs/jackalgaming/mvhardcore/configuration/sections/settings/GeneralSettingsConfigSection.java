package gg.ggs.jackalgaming.mvhardcore.configuration.sections.settings;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;

public class GeneralSettingsConfigSection extends ConfigSection {
    private final WorldLocationConfigSection respawnRedirectLocation;

    /**
     * @param configFile
     * @param sectionPath
     */
    public GeneralSettingsConfigSection(ConfigFile configFile, String sectionPath) {
        super(configFile, sectionPath);

        this.respawnRedirectLocation = new WorldLocationConfigSection(configFile, getValuePath("respawnLocation"));
    }

    @Override
    public void loadSection() {
        this.respawnRedirectLocation.loadSection();
    }

    public WorldLocationConfigSection getRespawnLocation() {
        return respawnRedirectLocation;
    }
}
