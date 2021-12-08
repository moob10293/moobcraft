package me.moob.hardersurvival;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CommandCustomTag implements CommandExecutor {
    Plugin plugin;

    CommandCustomTag(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 4){
            Player player = Bukkit.getPlayerExact(args[0]);
            NamespacedKey namespacedKey = new NamespacedKey(plugin,args[1]);
            if (player != null){
                if (Objects.equals(args[2], "int")){
                    player.getPersistentDataContainer().set(namespacedKey, PersistentDataType.INTEGER, Integer.parseInt(args[3]));
                    return true;
                } else if (Objects.equals(args[2], "byte")) {
                    player.getPersistentDataContainer().set(namespacedKey, PersistentDataType.BYTE, (byte) Integer.parseInt(args[3]));
                    return true;
                }
            }
        }
    return false;
    }
}