package me.moob.hardersurvival;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.boss.KeyedBossBar;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Iterator;
import java.util.Objects;

//this class handles things related to damage.
//this class deals with spawning skeletons/spiders in response to players attacking from 3 blocks up,
//and making mobs do double damage

public class MobDamageEvent implements Listener {

    @EventHandler
    public void whenDamage(EntityDamageByEntityEvent event) {

        Entity attacker = event.getDamager();
        LivingEntity defender = ((LivingEntity) event.getEntity());
        World world = defender.getWorld();
        Settings settings = new Settings();
        EntityType attacker_type = attacker.getType();
        EntityType defender_type = defender.getType();

        if (attacker instanceof Monster && attacker_type != EntityType.PLAYER) {//double damage for every entity except for the player
            event.setDamage(event.getDamage() * settings.damage_times);
        }

        if (attacker_type == EntityType.PLAYER) {
            String bossText;
            double maxHearts = Objects.requireNonNull(defender.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue()/2;
            if (defender.getCustomName() != null) {
                bossText = defender.getCustomName() + "hearts: " + defender.getHealth()/2 + "/" + maxHearts;
            } else {
                bossText = defender_type + "hearts: " + defender.getHealth() / 2 + "/" + maxHearts;
            }
            BossBar bossBar = Bukkit.createBossBar(bossText, BarColor.RED, BarStyle.SOLID);
            //remove other heart boss bars
            for (Iterator<KeyedBossBar> it = Bukkit.getBossBars(); it.hasNext(); ) {
                BossBar bar = it.next();
                if (bar.getPlayers().contains((Player) attacker)) {
                    bar.removePlayer((Player) attacker);
                }
            }
            bossBar.addPlayer((Player) attacker);
        }

        if (attacker_type == EntityType.PLAYER && defender_type != EntityType.PLAYER) {//if the entity isn't able to attack pillar players on its own

            Location defender_location = defender.getLocation();
            attacker.getServer().broadcastMessage("goes not the player");

            if ((attacker.getLocation().getY() - defender_location.getY()) >= settings.attack_cheat_height
                    && !(defender instanceof Skeleton) && !(defender instanceof Spider)) {

                Spawn spawn = new Spawn();
                attacker.getServer().broadcastMessage("Hey! You're cheating!");

                if (defender_type == EntityType.IRON_GOLEM) {
                    attacker.getServer().broadcastMessage("The IRON GOLEM breaks stuff!");
                    attacker.getLocation().add(0, -1, 0).getBlock().breakNaturally();
                    attacker.getLocation().add(0, -2, 0).getBlock().breakNaturally();
                } else {
                    if (settings.getSummon_reinforcements()
                            && defender.getLocation().getChunk().getEntities().length <= settings.spawn_limit) {

                        if (settings.getReinforcement_is_a_skeleton()) {
                            spawn.spawnOne(defender_location, world, settings.spawn_distance, EntityType.SKELETON);
                        } else {
                            spawn.spawnOne(defender_location, world, settings.spawn_distance, EntityType.SPIDER);
                        }
                    }
                }
            }
        }
    }
}
