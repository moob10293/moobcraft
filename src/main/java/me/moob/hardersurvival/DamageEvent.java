package me.moob.hardersurvival;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageEvent implements Listener {

    @EventHandler
    public void whenDamage(EntityDamageEvent event) {

        LivingEntity entity = (LivingEntity) event.getEntity();
        World world = entity.getWorld();

        for (Player player : world.getPlayers()) {
            player.sendMessage(entity.getName());
        }
    }
}
