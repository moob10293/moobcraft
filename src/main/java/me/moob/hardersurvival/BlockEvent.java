package me.moob.hardersurvival;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;

import java.util.Set;

//this class detects when a player places or breaks blocks and adds it to the list of blocks that
//mobs should destroy and remove it from the list when mobs destroy it

public class BlockEvent implements Listener {
    Set<Location> listOfPlayerBlocks;

    BlockEvent(Set<Location> list) {
        this.listOfPlayerBlocks = list;
    }

    @EventHandler
    public void whenEntity(EntityChangeBlockEvent event) {
        Location blockLocation = event.getBlock().getLocation();
        Entity entity = event.getEntity();
        if (entity instanceof Monster) {
            //no if (contains), because if it doesn't contain it, it just returns null
            listOfPlayerBlocks.remove(blockLocation);
        }//delete from the list if a mob destroys or places a block that a player placed or destroyed
    }

    @EventHandler
    public void whenPlayerPlace(BlockPlaceEvent event){
        event.getPlayer().sendMessage("placed a block");
        listOfPlayerBlocks.add(event.getBlock().getLocation());
    }
}
