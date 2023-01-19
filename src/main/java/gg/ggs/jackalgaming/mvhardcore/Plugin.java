package gg.ggs.jackalgaming.mvhardcore;

import java.util.logging.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * mvhardcore java plugin
 */
public class Plugin extends JavaPlugin
{
  private static final Logger LOGGER=Logger.getLogger("mvhardcore");

  public void onEnable()
  {
    LOGGER.info("mvhardcore enabled");
  }

  public void onDisable()
  {
    LOGGER.info("mvhardcore disabled");
  }
}
