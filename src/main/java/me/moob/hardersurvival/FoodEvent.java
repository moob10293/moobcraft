package me.moob.hardersurvival;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

//this class takes care of the things regarding food(poisonous rotten flesh and less hunger points restored)

public class FoodEvent implements Listener {
    @EventHandler
    public void foodEvent(FoodLevelChangeEvent event) {

        ItemStack eaten_item = event.getItem();
        HumanEntity person = event.getEntity();

        if (eaten_item != null) {

            Material eaten_type = eaten_item.getType();

            if (eaten_type == Material.ROTTEN_FLESH) {//rotten flesh

                PotionEffect hungry = new PotionEffect(PotionEffectType.HUNGER, 100, 0);
                PotionEffect ouch = new PotionEffect(PotionEffectType.POISON, 100, 0);//initialises the effects to give
                PotionEffect dizzy = new PotionEffect(PotionEffectType.CONFUSION, 100, 0);
                person.addPotionEffect(ouch);//applies the effects
                person.addPotionEffect(hungry);
                person.addPotionEffect(dizzy);

            } else if (eaten_type == Material.CHICKEN) {

                PotionEffect hungry = new PotionEffect(PotionEffectType.HUNGER, 600, 0);//the chance used to be 30%, now it's 100%
                person.addPotionEffect(hungry);

            }
            eaten_item.setAmount(eaten_item.getAmount() - 1);
            int current_food_level = person.getFoodLevel();
            person.setFoodLevel(current_food_level + ((event.getFoodLevel() - current_food_level) / 2)); //increase hunger by only half
            event.setCancelled(true);
        }
    }
}