package me.moob.hardersurvival;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PlayerSpawnEvent implements Listener {

    Plugin plugin;

    PlayerSpawnEvent(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerSpawnEvent(PlayerJoinEvent event) {
        PersistentDataContainer playerPDC = event.getPlayer().getPersistentDataContainer();
        NamespacedKey kitKey = new NamespacedKey(plugin, "receivedkit");
        NamespacedKey attacksSurvived = new NamespacedKey(plugin, "attacksSurvived");
        NamespacedKey isFightingAttack =  new NamespacedKey(plugin, "isfightingattack");
        if (equalsOrDoesntHaveTag(playerPDC,kitKey,PersistentDataType.BYTE,(byte) 0)) {
            Player player = event.getPlayer();
            giveItem(player, Material.OAK_LOG, 32);
            giveItem(player, Material.COBBLESTONE, 64);
            giveItem(player, Material.COAL, 8);
            giveItem(player, Material.BREAD, 64);
            playerPDC.set(kitKey, PersistentDataType.BYTE, (byte) 1);
        }if (equalsOrDoesntHaveTag(playerPDC, attacksSurvived, PersistentDataType.INTEGER, null)){
            playerPDC.set(attacksSurvived, PersistentDataType.INTEGER, 0);
        } if(equalsOrDoesntHaveTag(playerPDC, isFightingAttack, PersistentDataType.BYTE,null)){
            playerPDC.set(isFightingAttack, PersistentDataType.BYTE, (byte) 0);
        }
    }

    void giveItem(Player player, Material material, int amount){
        player.getInventory().addItem(new ItemStack(material,amount));
    }

    public static boolean equalsOrDoesntHaveTag(@NotNull PersistentDataContainer persistentDataContainer, NamespacedKey key,
                                                PersistentDataType<?, ?> persistentDataType, Object equalvalue){
        return persistentDataContainer.has(key,persistentDataType)
                || persistentDataContainer.get(key,persistentDataType) == equalvalue;
    }
}
