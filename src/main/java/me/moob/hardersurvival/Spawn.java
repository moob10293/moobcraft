package me.moob.hardersurvival;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.Random;

//spawns in true minecraft fashion(10 attempts, and checks if the place is valid for each of the attempts)

public class Spawn {
    public Entity spawnOne(Location spawn_center, World world, int distance, EntityType entity) {

        Random random = new Random();

        for (int x = 0; x <= 10; x++) {//the ten attempts

            double x_spawn = randomPoint(spawn_center.getX(), distance, random);//getting a random location near the center
            double y_spawn = randomPoint(spawn_center.getY(), 2, random);
            double z_spawn = randomPoint(spawn_center.getZ(), distance, random);

            Location reinforcement_spawn_point = new Location(world, x_spawn, y_spawn, z_spawn);//checks if a 3 by 3 area is clear
            Location reinforcement_spawn_point2 = new Location(world, x_spawn + 1, y_spawn, z_spawn + 1);
            Location reinforcement_spawn_point3 = new Location(world, x_spawn - 1, y_spawn, z_spawn - 1);
            Location block_under = new Location(world, x_spawn, y_spawn - 1, z_spawn);//the block they spawn on
            Location head_location = new Location(world, x_spawn, y_spawn + 1, z_spawn);
            Location head_location2 = new Location(world, x_spawn, y_spawn + 2, z_spawn);

            if (reinforcement_spawn_point.getBlock().getType() == Material.AIR &&
                    block_under.getBlock().getType() != Material.AIR &&
                    head_location.getBlock().getType() == Material.AIR &&
                    reinforcement_spawn_point2.getBlock().getType() == Material.AIR &&
                    reinforcement_spawn_point3.getBlock().getType() == Material.AIR &&
                    head_location2.getBlock().getType() == Material.AIR) {//checking if the blocks are air(or in the block_under's case, not air)
                return world.spawnEntity(reinforcement_spawn_point, entity);//so it only spawns one
            }
        }
    return null;
    }

    private double randomPoint(double point, int distance, Random random) {
        int positive = random.nextInt(2);
        int new_point_distance = random.nextInt(distance + 1);
        if (positive == 0) {
            new_point_distance = new_point_distance * -1;
        }
        return new_point_distance + point;
    }
}
