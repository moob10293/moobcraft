package me.moob.hardersurvival;

import org.bukkit.inventory.EquipmentSlot;

import java.util.ArrayList;
import java.util.Random;

//a class that stores the settings(not the player placed blocks, though)

public class Settings {
    int spawn_limit;
    int spawn_distance;
    int damage_times;
    int attack_cheat_height;
    int[] creeper_detect_range;
    int amount_to_explode;
    EquipmentSlot[] list_of_slots;
    Chance armor_chance;
    Chance another_monster;
    Chance no_animal;
    Chance summon_reinforcements;
    Chance reinforcement_is_a_skeleton;
    Chance sickFromRot;

    Settings() {
        this.armor_chance = new Chance(1, 3);
        this.another_monster = new Chance(3, 4);
        this.no_animal = new Chance(1, 2);
        this.summon_reinforcements = new Chance(1, 5);
        this.reinforcement_is_a_skeleton = new Chance(1, 2);
        this.sickFromRot = new Chance(4,5);
        this.spawn_limit = 50;
        this.spawn_distance = 5;
        this.damage_times = 2;
        this.attack_cheat_height = 2;
        this.list_of_slots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS,
                EquipmentSlot.FEET};
        this.creeper_detect_range = new int[]{-3, -2, -1, 0, 1, 2, 3,};
        this.amount_to_explode = creeper_detect_range.length * creeper_detect_range.length/2;
    }

    public Boolean getArmor_chance() {
        return armor_chance.getChance();
    }

    public Boolean getAnother_monster() {
        return another_monster.getChance();
    }

    public Boolean getNo_animal() {
        return no_animal.getChance();
    }

    public Boolean getSummon_reinforcements() {
        return summon_reinforcements.getChance();
    }

    public Boolean getReinforcement_is_a_skeleton() {
        return reinforcement_is_a_skeleton.getChance();
    }
}

class Chance {
    int num1, num2;

    Chance(int num1, int num2) {//gets a chance, numb1/num2
        this.num1 = num1;
        this.num2 = num2;
    }

    public boolean getChance() {
        Random random = new Random();

        ArrayList<Boolean> list_of_chances = new ArrayList<>();
        for (int x = 0; x <= num1; x++) {
            list_of_chances.add(true);
        }
        for (int x = 0; x <= num2 - num1; x++) {
            list_of_chances.add(false);
        }
        return list_of_chances.get(random.nextInt(list_of_chances.size()));
    }
}