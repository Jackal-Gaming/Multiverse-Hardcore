package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.onarandombox.commandhandler.Command;

import gg.ggs.jackalgaming.mvhardcore.MultiverseHardcore;

public abstract class BaseCommand extends Command {
    protected MultiverseHardcore mvhcPlugin;

    protected BaseCommand(MultiverseHardcore plugin) {
        super(plugin);
        this.mvhcPlugin = plugin;
    }

    protected static Set<String> getAllCapitalizationVariations(String... commands) {
        Set<String> variations = new HashSet<>();
        for (String command : commands ) { 
            variations.addAll(getAllCapitalizationVariations(command));
        }
        return variations;
    }

    private static Set<String> getAllCapitalizationVariations(String command) {
        Set<String> variations = new HashSet<>();
        double totalVariations = Math.pow(2, command.length()) - 1;
        for (int i = 0; i < totalVariations; i++) {
            String capitalizationFlags = Integer.toString(i, 2);
            capitalizationFlags = StringUtils.repeat("0", command.length() - capitalizationFlags.length()) + capitalizationFlags;
            StringBuilder cmdVariation = new StringBuilder();
            for (int commandCharIndex = 0; commandCharIndex < capitalizationFlags.length(); commandCharIndex++) {
                String cmdCharacter = command.substring(commandCharIndex, commandCharIndex + 1);
                String cmdCapitalizationFlag = capitalizationFlags.substring(commandCharIndex, commandCharIndex + 1);
                if (cmdCapitalizationFlag.equals("0")) {
                    //0 (lowercase)
                    cmdVariation.append(cmdCharacter.toLowerCase());
                } else {
                    //1 (uppercase)
                    cmdVariation.append(cmdCharacter.toUpperCase());
                }
            }
            variations.add(cmdVariation.toString());
        }
        return variations;
    }

    @Override
    public void runCommand(CommandSender sender, List<String> args) {
        try {
            if (sender instanceof Player) {
                runPlayerCommand(sender, args);
            } else {
                runConsoleCommand(sender, args);
            }
        } catch (Exception e) {
            mvhcPlugin.log(Level.SEVERE, "Error while executing command " + this.getCommandName() + ". Error: " +
                    e.getMessage());
        }
    }

    protected abstract void runPlayerCommand(CommandSender sender, List<String> args);

    protected void runConsoleCommand(CommandSender sender, List<String> args) {
        String errorMessage = this.getCommandUsage() + " can not be ran from the console";
        sender.sendMessage(errorMessage);
        throw new IllegalAccessError("[Multiverse-Hardcore] " + errorMessage);
    }
}
