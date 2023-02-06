package gg.ggs.jackalgaming.mvhardcore.configuration;

import java.io.IOException;
import java.util.logging.Level;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.settings.GeneralSettingsConfigSection;
import gg.ggs.jackalgaming.mvhardcore.configuration.sections.worlds.WorldListConfigSection;

public class HardcoreConfig extends ConfigFile {
    private final GeneralSettingsConfigSection generalSettingsSection;
    private final WorldListConfigSection worldListSection;
    private final MultiverseHardcore plugin;

    public HardcoreConfig(MultiverseHardcore plugin)
            throws IOException, SecurityException, IllegalArgumentException {
        super(plugin, "config.yml");

        this.plugin = plugin;
        this.generalSettingsSection = new GeneralSettingsConfigSection(this, "settings");
        this.worldListSection = new WorldListConfigSection(this, "worlds");
    }

    @Override
    public void loadConfig() {
        // Sections
        this.generalSettingsSection.loadSection();
        this.worldListSection.loadSection();

        this.save();
    }

    @Override
    public void log(Level level, String msg) {
        this.plugin.log(level, msg);
    }

    public GeneralSettingsConfigSection getGeneralSettings() {
        return this.generalSettingsSection;
    }

    public WorldListConfigSection getWorldListSection() {
        return this.worldListSection;
    }
}
