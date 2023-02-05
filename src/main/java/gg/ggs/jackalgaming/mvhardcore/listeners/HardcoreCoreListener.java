package gg.ggs.jackalgaming.mvhardcore.listeners;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.onarandombox.MultiverseCore.event.MVConfigReloadEvent;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class HardcoreCoreListener implements Listener {
    private final MultiverseHardcore plugin;

    public HardcoreCoreListener(MultiverseHardcore plugin) {
        this.plugin = plugin;
    }

    /**
     * This method is called when Multiverse-Core wants to reload config files.
     *
     * @param event The Config Reload event.
     * @throws IOException
     * @throws IllegalArgumentException
     * @throws SecurityException
     */
    @EventHandler
    public void configReloadEvent(MVConfigReloadEvent event) throws SecurityException, IllegalArgumentException, IOException {
        this.plugin.reloadConfig();
        event.addConfig("Multiverse-Hardcore - config.yml");
    }
}
