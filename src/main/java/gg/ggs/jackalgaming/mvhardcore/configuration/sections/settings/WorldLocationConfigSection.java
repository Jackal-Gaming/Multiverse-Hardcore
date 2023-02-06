package gg.ggs.jackalgaming.mvhardcore.configuration.sections.settings;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import gg.ggs.jackalgaming.configuration.ConfigFile;
import gg.ggs.jackalgaming.configuration.ConfigSection;
import gg.ggs.jackalgaming.configuration.ConfigValue;

public class WorldLocationConfigSection extends ConfigSection {
    private final ConfigValue<String> worldName;
    private final ConfigValue<Double> x;
    private final ConfigValue<Double> y;
    private final ConfigValue<Double> z;
    private final ConfigValue<Float> yaw;
    private final ConfigValue<Float> pitch;

    /**
     * @param configFile
     * @param sectionName
     */
    public WorldLocationConfigSection(ConfigFile configFile, String sectionName) {
        super(configFile, sectionName);

        this.worldName = new ConfigValue<>(configFile, this.getValuePath("world"), "");
        this.x = new ConfigValue<>(configFile, this.getValuePath("x"), 0.0);
        this.y = new ConfigValue<>(configFile, this.getValuePath("y"), 0.0);
        this.z = new ConfigValue<>(configFile, this.getValuePath("z"), 0.0);
        this.yaw = new ConfigValue<>(configFile, this.getValuePath("yaw"), (float) 0.0);
        this.pitch = new ConfigValue<>(configFile, this.getValuePath("pitch"), (float) 0.0);
    }

    @Override
    public void loadSection() {
        configFile.log(Level.INFO, "Loading Section: " + getPath());
        List<ConfigValue<String>> stringConfigValues = Arrays.asList(worldName);
        List<ConfigValue<Double>> doubleConfigValues = Arrays.asList(x, y, z);
        List<ConfigValue<Float>> floatConfigValues = Arrays.asList(yaw, pitch);
        configFile.setDefaults(stringConfigValues);
        configFile.setDefaults(doubleConfigValues);
        configFile.setDefaults(floatConfigValues);
    }

    public Location getLocation(JavaPlugin plugin) {
        World world = plugin.getServer().getWorld(this.worldName.getValueFromConfig());
        Double xValue = this.x.getValueFromConfig();
        Double yValue = this.y.getValueFromConfig();
        Double zValue = this.z.getValueFromConfig();
        Float yawValue = this.yaw.getValueFromConfig();
        Float pitchValue = this.pitch.getValueFromConfig();

        return new Location(world, xValue, yValue, zValue, yawValue, pitchValue);
    }
}
