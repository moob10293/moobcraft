package me.moob.hardersurvival;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Set;

public class OnTick {
    World world;
    Set<Location> list_of_player_blocks;
    Plugin plugin;

    OnTick(World temporary_world, Set<Location> temporary_list, Plugin temporary_plugin) {
        world = temporary_world;
        list_of_player_blocks = temporary_list;
        plugin = temporary_plugin;
    }

    public void explodeCreepers() {
        List<Entity> list_of_creepers = world.getEntities();
        Settings settings = new Settings();
        list_of_creepers.removeIf(entity -> entity.getType() != EntityType.CREEPER);
        for (Entity entity : list_of_creepers) {
            Location location = entity.getLocation().getBlock().getLocation();
            long amount_of_player_blocks = 0;
            for (int add_to_x : settings.creeper_detect_range) {
                for (int add_to_y : settings.creeper_detect_range) {
                    for (int add_to_z : settings.creeper_detect_range) {
                        Location temp_location = location.clone().add(add_to_x, add_to_y, add_to_z);
                        if (list_of_player_blocks.contains(temp_location)) {
                            amount_of_player_blocks++;
                        }
                    }
                }
            }
            if (amount_of_player_blocks >= 9) {
                list_of_creepers.get(0).getServer().broadcastMessage("commencing stuff");
                //((Creeper) entity).setFuseTicks(100);//5 second fuse
                ((Creeper) entity).ignite();
                //entity.setMetadata("exploding", new MetadataValue(1))
                //((Creeper) entity).explode();
            }
        }
    }

    public void IfNight() {
        if (12000 < world.getTime() && world.getTime() < 23000) {
            NamespacedKey isFightingAttack = new NamespacedKey(plugin, "isfightingattack");
            for (Player player : world.getPlayers()) {
                if (player.getPersistentDataContainer().get(isFightingAttack, PersistentDataType.BYTE) == (byte) 0) {
                    Location spawn = player.getBedSpawnLocation();//get spawn for respawn anchor too
                    if (spawn != null) {
                        //direction = random direction from [[1,0],[-1,0],[0,1],[0,-1]];left,right,up,down(subject to change)
                        //spawn 10+2*timesplayersurvivedsieges mobs in that
                        //Location locationtospawnat = direction.add(somedistance*direction[0],0,[somedistance*direction[1])
                        Spawn spawn1 = new Spawn();
                        //for (int x = 0; x < player.getattackssurvived*2+10; x++){
                        //    ((Mob) spawn1.spawnOne(locationtospawnat, world, 5, EntityType.CREEPER)).setTarget(player);
                        //}
                    }
                }
            }
        }
    }
}
