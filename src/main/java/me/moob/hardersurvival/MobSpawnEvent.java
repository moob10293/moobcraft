package me.moob.hardersurvival;


import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

//this class deals with spawning
//this class gives leather armor to 1/3 of hostile mobs that wear armor, let the mobs pick up items,
//summons another same monster 3/4 of the time when a monster spawns
//(the summoned monster has a 3/4 chance of summoning another monster),
//stopping 1/2 of normal eatable mob spawns(cow,pig,chicken,sheep)

public class MobSpawnEvent implements Listener {
    @EventHandler
    public void whenSpawn(EntitySpawnEvent event) {

        Entity spawned = event.getEntity();
        EntityType spawned_type = spawned.getType();
        World spawn_world = spawned.getWorld();
        Settings settings = new Settings();
        spawn_world.setMonsterSpawnLimit(settings.spawn_limit);
        Spawn spawn = new Spawn();

        if (spawned instanceof Zombie || spawned instanceof Skeleton) {

            //giving 1/3 of monsters that can wear armor, leather armor, in their empty armor slots
            LivingEntity living_spawned = (LivingEntity) spawned;
            living_spawned.setCanPickupItems(true);

            if (settings.getArmor_chance() && living_spawned.getEquipment() != null) {

                EntityEquipment equipment_spawned = living_spawned.getEquipment();
                ItemStack[][] list_of_equipment = {{equipment_spawned.getItem(EquipmentSlot.HEAD),
                        new ItemStack(Material.GOLDEN_HELMET)},//each list in list has the current armor and the armor to replace
                        {equipment_spawned.getItem(EquipmentSlot.CHEST),
                                new ItemStack(Material.GOLDEN_CHESTPLATE)},
                        {equipment_spawned.getItem(EquipmentSlot.LEGS),
                                new ItemStack(Material.GOLDEN_LEGGINGS)},
                        {equipment_spawned.getItem(EquipmentSlot.FEET),
                                new ItemStack(Material.GOLDEN_BOOTS)}};//using a list instead of if/else just to show I know how to use lists

                for (int x = 0; x < 4; ++x) {
                    if (list_of_equipment[x][0].getType() == Material.AIR) {
                        equipment_spawned.setItem(settings.list_of_slots[x], list_of_equipment[x][1], true);
                    }//quietly equip leather armor on unarmored slots
                }
            }
        }


        if (spawned.getLocation().getChunk().getEntities().length <= settings.spawn_limit) {//amount of mobs in chunk less than 50
            if (settings.getAnother_monster() && spawned instanceof Monster) {//that an extra monster spawns(that monster also has a 3/4 chance)
                spawn.spawnOne(spawned.getLocation(), spawned.getWorld(), settings.spawn_distance, spawned.getType());
            }
        } else {
            spawned.getServer().broadcastMessage("more than 50 monsters");
        }
    }
}