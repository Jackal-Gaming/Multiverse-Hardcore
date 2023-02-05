package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;
import gg.ggs.jackalgaming.mvhardcore.configuration.HardcoreConfig;

public class InfoCommand extends BaseCommand {
    static final Permission PERMISSION = new Permission(
            "multiverse.hardcore.info",
            "Returns detailed information on the ongoing hardcore world, if any",
            PermissionDefault.FALSE);

    public InfoCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.setName("Ongoing World Information");
        this.setCommandUsage("/" + plugin.getCommandAlias() + " info ");
        this.setArgRange(0, 1);
        this.addKey(plugin.getCommandAlias() + " info");
        this.addKey(plugin.getCommandAlias() + "info");
        this.addKey(plugin.getCommandAlias() + "i");
        this.setPermission(PERMISSION);
    }

    @Override
    protected void runPlayerCommand(CommandSender sender, List<String> args) {
        String hardcoreWorldName = this.mvhcPlugin.getConfig().getString(HardcoreConfig.WORLD_NAME.getPath());
        boolean doesHardcoreWorldExist = this.mvhcPlugin.getCore().getMVWorldManager().isMVWorld(hardcoreWorldName);

        sender.sendMessage(new String[]{
            "Hello, : " + sender.getName(),
            "Hardcore World: " + hardcoreWorldName,
            "Exists?: " + doesHardcoreWorldExist
        });
    }
}
