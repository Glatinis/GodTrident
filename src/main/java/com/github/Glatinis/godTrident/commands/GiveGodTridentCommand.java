package com.github.Glatinis.godTrident.commands;

import com.github.Glatinis.godTrident.GodTridentItem;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public final class GiveGodTridentCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("godtrident.give")) {
            sender.sendRichMessage("<red>You do not have permission to use this command.</red>");
            return true;
        }

        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                sender.sendRichMessage("<yellow>Usage: /givegodtrident <player></yellow>");
                return true;
            }
            target = player;
        } else {
            target = Bukkit.getPlayerExact(args[0]);
            if (target == null) {
                sender.sendRichMessage("<red>Player '<white>" + args[0] + "</white>' not found or is not online.</red>");
                return true;
            }
        }

        target.getInventory().addItem(GodTridentItem.create());
        sender.sendRichMessage("<green>Gave God Trident to <white>" + target.getName() + "</white>.</green>");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("godtrident.give")) return List.of();
        if (args.length == 1) {
            String prefix = args[0].toLowerCase();
            return Bukkit.getOnlinePlayers().stream()
                .map(Player::getName)
                .filter(name -> name.toLowerCase().startsWith(prefix))
                .toList();
        }
        return List.of();
    }
}
