package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public class RegisterCommand extends BaseCommand {
    static final Permission PERMISSION = new Permission(
            "multiverse.hardcore.register",
            "Register an existing Multiverse world as a hardcore world",
            PermissionDefault.FALSE);

    static final String HEADER = ChatColor.GOLD + "====[ Multiverse-Hardcore Register ]====";
    static final String USAGE = " register " + ChatColor.GOLD + "[WORLD]";
    private String commandAlias;

    public RegisterCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.commandAlias = plugin.getCommandAlias();
        this.setName("Register Multiverse-Hardcore World");
        this.setCommandUsage("/" + commandAlias + USAGE);
        this.setArgRange(1, 1);

        String[] commandVariations = new String[] {
            commandAlias + " register",
            commandAlias + "register",
            commandAlias + "r",
            commandAlias + " link"
        };
        for (String command : commandVariations) {
            this.addKey(command);
        }

        this.setPermission(PERMISSION);
    }

    @Override
    protected void runPlayerCommand(CommandSender sender, List<String> args) {
        registerWorld(sender, args.get(0));
    }

    private void registerWorld(CommandSender sender, String worldName) {
        boolean isWorldInConfig = this.mvhcPlugin.getHardcoreConfig().getWorldListSection().getWorldConfigMap().containsKey(worldName);
        boolean doesMultiverseWorldExist = this.mvhcPlugin.getCore().getMVWorldManager().isMVWorld(worldName);

        if (isWorldInConfig) {
            sender.sendMessage(ChatColor.RED + "World is already registered with Multiverse-Hardcore");
            return;
        }

        if (!doesMultiverseWorldExist) {
            sender.sendMessage(ChatColor.RED + "That world can not be found in Multiverse");
            return;
        }

        this.mvhcPlugin.getHardcoreConfig().getWorldListSection().addWorldConfigSection(worldName);
        this.mvhcPlugin.getHardcoreConfig().save();
        sender.sendMessage(ChatColor.AQUA + worldName + " registered with Multiverse-Hardcore");
    }
}
