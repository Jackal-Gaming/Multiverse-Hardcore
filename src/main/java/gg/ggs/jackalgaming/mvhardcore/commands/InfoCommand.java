package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class InfoCommand extends BaseCommand {
    static final Permission PERMISSION = new Permission(
            "multiverse.hardcore.info",
            "Returns detailed information about the specified hardcore world",
            PermissionDefault.FALSE);

    static final String HEADER = ChatColor.GOLD + "====[ Multiverse-Hardcore Info ]====";
    static final String USAGE = " info " + ChatColor.GOLD + "[WORLD]";
    private String commandAlias;

    public InfoCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.commandAlias = plugin.getCommandAlias();
        this.setName("Multiverse Hardcore Info");
        this.setCommandUsage("/" + commandAlias + USAGE);
        this.setArgRange(1, 1);

        String[] commandVariations = new String[] {
                commandAlias + " info",
                commandAlias + "info",
                commandAlias + "i"
        };
        for (String command : commandVariations) {
            this.addKey(command);
        }

        this.setPermission(PERMISSION);
    }

    @Override
    protected void runPlayerCommand(CommandSender sender, List<String> args) {
        listOutWorldInfo(sender, args.get(0));
    }

    private void listOutWorldInfo(CommandSender sender, String worldName) {
        boolean doesMultiverseWorldExist = this.mvhcPlugin.getCore().getMVWorldManager().isMVWorld(worldName);

        String matchingMvWorldName = doesMultiverseWorldExist
                ? this.mvhcPlugin.getCore().getMVWorldManager().getMVWorld(worldName).getName()
                : worldName;
        boolean isWorldInConfig = this.mvhcPlugin.getHardcoreConfig().getWorldListSection().getWorldConfigMap()
                .containsKey(matchingMvWorldName);

        if (!isWorldInConfig) {
            sender.sendMessage(ChatColor.RED + "That world is not registered with Multiverse-Hardcore");
        } else {
            sender.sendMessage(new String[] {
                    HEADER,
                    ChatColor.GOLD + "World Name: " + worldName,
                    ChatColor.AQUA + "Multiverse World: " + doesMultiverseWorldExist
            });
        }
    }
}
