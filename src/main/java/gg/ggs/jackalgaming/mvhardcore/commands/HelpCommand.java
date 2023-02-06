package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class HelpCommand extends BaseCommand {
    static final Permission PERMISSION = new Permission(
            "multiverse.hardcore.help",
            "Returns a nice help menu",
            PermissionDefault.FALSE);

    static final String HEADER = ChatColor.GOLD + "====[ Multiverse-Hardcore Help ]====";
    static final String USAGE = " help";
    private String commandAlias;

        
    public HelpCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.commandAlias = plugin.getCommandAlias();
        this.setName("Get Help with Multiverse-Hardcore");
        this.setCommandUsage("/" + commandAlias + USAGE);
        this.setArgRange(0, 0);

        String[] commandVariations = new String[] {
            commandAlias,
            commandAlias + " help",
            commandAlias + "help",
            commandAlias + "h"
        };
        for (String command : commandVariations) {
            this.addKey(command);
        }

        this.setPermission(PERMISSION);
    }

    @Override
    protected void runPlayerCommand(CommandSender sender, List<String> args) {
        sender.sendMessage(new String[] {
            HEADER,
            ChatColor.AQUA + "/" + commandAlias + HelpCommand.USAGE,
            ChatColor.AQUA + "/" + commandAlias + ListCommand.USAGE,
            ChatColor.AQUA + "/" + commandAlias + InfoCommand.USAGE,
        });
    }
}
