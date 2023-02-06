package gg.ggs.jackalgaming.mvhardcore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVPlugin;
import com.onarandombox.commandhandler.CommandHandler;

import gg.ggs.jackalgaming.mvhardcore.commands.HelpCommand;
import gg.ggs.jackalgaming.mvhardcore.commands.InfoCommand;
import gg.ggs.jackalgaming.mvhardcore.commands.ListCommand;
import gg.ggs.jackalgaming.mvhardcore.commands.RegisterCommand;
import gg.ggs.jackalgaming.mvhardcore.configuration.HardcoreConfig;
import gg.ggs.jackalgaming.mvhardcore.listeners.HardcoreListener;
import gg.ggs.jackalgaming.mvhardcore.listeners.HardcoreMultiverseCoreListener;
import gg.ggs.jackalgaming.mvhardcore.services.PlayerService;

/*
 * mvhardcore java plugin
 */
public class MultiverseHardcore extends JavaPlugin implements MVPlugin {

  private Logger logger;
  private String name;
  private String commandAlias = "mvhc";

  // External Plugin
  private String multiverCorePluginName = "Multiverse-Core";
  private int minimumMultiverseCoreVersion = 22;
  private MultiverseCore mvCore;

  // Services
  private HardcoreConfig hardcoreConfig;
  private PlayerService playerService;
  private CommandHandler cmdHandler;

  @Override
  public void onEnable() {
    try {
      this.name = this.getName();
      this.logger = Logger.getLogger(this.name);

      // Register DI instances
      this.mvCore = loadMultiverseCore();
      reloadConfig();
      this.playerService = new PlayerService(this);
      this.cmdHandler = loadCommands();
      this.getCore().incrementPluginCount();

      // Register events
      registerEvents();

      log(Level.INFO, this.name + " enabled");
    } catch (Exception e) {
      log(Level.WARNING, "Failed to load. Error: " + e.getMessage());
      this.getServer().getPluginManager().disablePlugin(this);
    }
  }

  private MultiverseCore loadMultiverseCore() throws IllegalStateException {
    // Retrieve plugin
    MultiverseCore multiverseCorePlugin = (MultiverseCore) this.getServer().getPluginManager()
        .getPlugin(this.multiverCorePluginName);
    if (multiverseCorePlugin == null) {
      log(Level.SEVERE, "Unable to load dependent plugin: " + this.multiverCorePluginName);
      this.getServer().getPluginManager().disablePlugin(this);
      throw new IllegalStateException("Unable to load dependent plugin");
    }

    // Ensure version is valid
    if (multiverseCorePlugin.getProtocolVersion() < this.minimumMultiverseCoreVersion) {
      log(Level.SEVERE, "Your Multiverse-Core is OUT OF DATE");
      log(Level.SEVERE, "This version of Multiverse-Inventories requires Protocol Level: " +
          this.minimumMultiverseCoreVersion);
      log(Level.SEVERE, "Your of Core Protocol Level is: " + multiverseCorePlugin.getProtocolVersion());
      log(Level.SEVERE, "Grab an updated copy at: ");
      log(Level.SEVERE, "http://bukkit.onarandombox.com/?dir=multiverse-core");
      throw new IllegalStateException("dependent plugin is out of date");
    }

    return multiverseCorePlugin;
  }

  public HardcoreConfig getHardcoreConfig() {
    return hardcoreConfig;
  }

  public PlayerService getPlayerService() {
    return playerService;
  }

  @NotNull
  @Override
  public FileConfiguration getConfig() {
    if (this.hardcoreConfig == null) {
      reloadConfig();
    }
    return this.hardcoreConfig.getConfig();
  }

  @Override
  public void reloadConfig() {
    try {
      log(Level.INFO, "Loading Config");
      this.hardcoreConfig = new HardcoreConfig(this);
      hardcoreConfig.loadConfig();
    } catch (SecurityException | IllegalArgumentException | IOException e) {
      log(Level.INFO, "Error while Loading Config: " + e.getMessage());
    }
  }

  private CommandHandler loadCommands() {
    log(Level.INFO, "Loading Commands");

    CommandHandler commandHandler = this.mvCore.getCommandHandler();
    commandHandler.registerCommand(new HelpCommand(this));
    commandHandler.registerCommand(new ListCommand(this));
    commandHandler.registerCommand(new InfoCommand(this));
    commandHandler.registerCommand(new RegisterCommand(this));

    for (com.onarandombox.commandhandler.Command c : commandHandler.getAllCommands()) {
      if (c instanceof com.onarandombox.MultiverseCore.commands.HelpCommand) {
        c.addKey(this.commandAlias);
      }
    }

    return commandHandler;
  }

  private void registerEvents() {
    PluginManager pm = this.getServer().getPluginManager();

    pm.registerEvents(new HardcoreListener(this), this);
    pm.registerEvents(new HardcoreMultiverseCoreListener(this), this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {

    log(Level.WARNING, "Running command: " + command.getName());
    if (!this.isEnabled()) {
      sender.sendMessage("This plugin is Disabled!");
      return true;
    }
    ArrayList<String> allArgs = new ArrayList<>(Arrays.asList(args));
    allArgs.add(0, command.getName());
    return this.cmdHandler.locateAndRunCommand(sender, allArgs);
  }

  @Override
  public void onDisable() {
    log(Level.INFO, this.name + " disabled");
  }

  public String getCommandAlias() {
    return commandAlias;
  }

  @Override
  public void log(Level level, String msg) {
    if (logger != null) {
      logger.log(level, msg);
    }
  }

  @Override
  public String dumpVersionInfo(String buffer) {
    buffer += logAndAddToPasteBinBuffer("Multiverse-Hardcore Version: " + this.getDescription().getVersion());
    return buffer;
  }

  private String logAndAddToPasteBinBuffer(String string) {
    this.log(Level.INFO, string);
    return "[Multiverse-Hardcore] " + string + '\n';
  }

  @Override
  public MultiverseCore getCore() {
    return this.mvCore;
  }

  @Override
  public void setCore(MultiverseCore core) {
    this.mvCore = core;
  }

  @Override
  public int getProtocolVersion() {
    return 1;
  }
}
