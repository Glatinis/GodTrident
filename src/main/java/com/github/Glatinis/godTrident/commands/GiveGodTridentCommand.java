package com.github.Glatinis.godTrident.commands;

import com.github.Glatinis.godTrident.GodTridentItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class GiveGodTridentCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage("You do not have permission to use this command.");
            return true;
        }

        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Usage: /givegodtrident <player>");
                return true;
            }
            target = player;
        } else {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendMessage("Player '" + args[0] + "' not found or is not online.");
                return true;
            }
        }

        target.getInventory().addItem(GodTridentItem.create());
        sender.sendMessage("Gave God Trident to " + target.getName() + ".");
        return true;
    }
}
