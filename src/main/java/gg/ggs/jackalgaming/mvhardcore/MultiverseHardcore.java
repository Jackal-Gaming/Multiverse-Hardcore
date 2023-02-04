package gg.ggs.jackalgaming.mvhardcore;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import com.dumptruckman.minecraft.util.Logging;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVPlugin;
import com.onarandombox.MultiverseCore.commands.HelpCommand;
import com.onarandombox.commandhandler.CommandHandler;

import gg.ggs.jackalgaming.mvhardcore.commands.InfoCommand;

/*
 * mvhardcore java plugin
 */
public class MultiverseHardcore extends JavaPlugin implements MVPlugin {
  private String name;

  private String multiverCorePluginName = "Multiverse-Core";
  private int minimumMultiverseCoreVersion = 22;
  private MultiverseCore mvCore;

  private String commandAlias = "mvhc";
  public String getCommandAlias() {
    return commandAlias;
  }

  private CommandHandler commandHandler;

  private CommandHandler getCommandHandler() {
    return this.commandHandler;
  }

  @Override
  public void onEnable() {
    Logging.init(this);
    log(Level.INFO, "Attempting to enable " + this.name);

    // Register DI instances
    this.name = this.getName();

    try {
      loadMultiverseCore();
      loadCommands();
    } catch (Exception e) {
      this.getServer().getPluginManager().disablePlugin(this);
      log(Level.WARNING, "Failed to load. Error: " + e.getMessage());
      return;
    }

    // Register events

    log(Level.INFO, this.name + " enabled");
  }

  private void loadMultiverseCore() {
    // Retrieve plugin
    this.mvCore = (MultiverseCore) this.getServer().getPluginManager().getPlugin(this.multiverCorePluginName);
    if (this.mvCore == null) {
      log(Level.SEVERE, "Unable to load dependent plugin: " + this.multiverCorePluginName);
      this.getServer().getPluginManager().disablePlugin(this);
      throw new IllegalStateException("Unable to load dependent plugin");
    }

    // Ensure version is valid
    if (this.mvCore.getProtocolVersion() < this.minimumMultiverseCoreVersion) {
      log(Level.SEVERE, "Your Multiverse-Core is OUT OF DATE");
      log(Level.SEVERE, "This version of Multiverse-Inventories requires Protocol Level: " +
          this.minimumMultiverseCoreVersion);
      log(Level.SEVERE, "Your of Core Protocol Level is: " + this.mvCore.getProtocolVersion());
      log(Level.SEVERE, "Grab an updated copy at: ");
      log(Level.SEVERE, "http://bukkit.onarandombox.com/?dir=multiverse-core");
      throw new IllegalStateException("dependent plugin is out of date");
    }
  }

  private void loadCommands() {
    this.commandHandler = this.mvCore.getCommandHandler();
    this.commandHandler.registerCommand(new InfoCommand(this));

    for (com.onarandombox.commandhandler.Command c : this.commandHandler.getAllCommands()) {
      if (c instanceof HelpCommand) {
        c.addKey(this.commandAlias);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
      @NotNull String label, @NotNull String[] args) {
    if (!this.isEnabled()) {
      sender.sendMessage("This plugin is Disabled!");
      return true;
    }
    ArrayList<String> allArgs = new ArrayList<>(Arrays.asList(args));
    allArgs.add(0, command.getName());
    return this.getCommandHandler().locateAndRunCommand(sender, allArgs);
  }

  @Override
  public void onDisable() {
    log(Level.INFO, this.name + " disabled");
  }

  @Override
  public void onLoad() {
      getDataFolder().mkdirs();
  }

  @Override
  public void log(Level level, String msg) {
    Logging.log(level, msg);    
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