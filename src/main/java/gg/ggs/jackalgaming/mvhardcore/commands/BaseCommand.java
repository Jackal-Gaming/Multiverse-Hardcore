package gg.ggs.jackalgaming.mvhardcore.commands;

import java.util.List;
import java.util.logging.Level;

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
        String errorMessage = this.getCommandName() + " can not be ran from the console";
        sender.sendMessage(errorMessage);
        throw new IllegalAccessError("TODO: IS THIS SEEN?? Some IllegalAccessError thrown in the BaseCommand. message: " + errorMessage);
    }
}
