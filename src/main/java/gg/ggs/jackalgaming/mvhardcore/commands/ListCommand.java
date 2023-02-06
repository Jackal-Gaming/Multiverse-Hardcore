package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class ListCommand extends BaseCommand {
    static final Permission PERMISSION = new Permission(
            "multiverse.hardcore.list",
            "Returns list of registered hardcore worlds",
            PermissionDefault.FALSE);

    static final String HEADER = ChatColor.GOLD + "====[ Multiverse-Hardcore World List ]====";
    static final String USAGE = " list";
    private String commandAlias;

    public ListCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.commandAlias = plugin.getCommandAlias();
        this.setName("Multiverse Hardcore List");
        this.setCommandUsage("/" + commandAlias + USAGE);
        this.setArgRange(0, 0);

        String[] commandVariations = new String[] {
            commandAlias + " list",
            commandAlias + "list",
            commandAlias + "l"
        };
        for (String command : commandVariations) {
            this.addKey(command);
        }

        this.setPermission(PERMISSION);
    }

    @Override
    protected void runPlayerCommand(CommandSender sender, List<String> args) {
        
        listOutHardcoreWorlds(sender);
    }

    private void listOutHardcoreWorlds(CommandSender sender) {
        Set<String> worldNames = this.mvhcPlugin.getHardcoreConfig().getWorldListSection().getWorldConfigMap().keySet();
        sender.sendMessage(new String[] {
            HEADER,
        });
        for (String worldName : worldNames) {
            sender.sendMessage(ChatColor.GOLD + worldName);
        }
    }
}
