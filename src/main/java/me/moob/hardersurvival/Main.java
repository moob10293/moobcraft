package me.moob.hardersurvival;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

//use something.getdatafolder
public class Main extends JavaPlugin {
    FileWriteRead fileWriteRead;
    @Override
    public void onEnable() {
        // Plugin startup logic
        World world = getServer().getWorlds().get(0);
        Set<Location> listOfPlayerBlocks = new HashSet<>();

        try {

            this.fileWriteRead = new FileWriteRead(listOfPlayerBlocks,getDataFolder().getAbsolutePath()+"/playerBlocks");
            if (!new File(getDataFolder().getAbsolutePath()+"/playerBlocks").createNewFile()) {
                fileWriteRead.fileRead();
            }

        } catch (IOException ignored) {
        }
        OnTick ontick = new OnTick(world, listOfPlayerBlocks, this);
        getServer().getPluginManager().registerEvents(new FoodEvent(), this);//using the plugins
        getServer().getPluginManager().registerEvents(new MobDamageEvent(), this);
        getServer().getPluginManager().registerEvents(new MobSpawnEvent(), this);
        getServer().getPluginManager().registerEvents(new BlockEvent(listOfPlayerBlocks), this);
        getServer().getPluginManager().registerEvents(new PlayerSpawnEvent(this), this);
        Objects.requireNonNull(this.getCommand("customtag")).setExecutor(new CommandCustomTag(this));

        new BukkitRunnable() {
            @Override
            public void run() {
                ontick.explodeCreepers();
            }//something I got from the spigot discord
        }.runTaskTimer(this, 0, 10);//run every 10 ticks
    }

    @Override
    public void onDisable() {
        try {
            fileWriteRead.fileWrite();
        } catch (IOException ignored) {
        }
        // Plugin shutdown logic

    }
}
class FileWriteRead {
    File file;
    Set<Location> locationSet;

    FileWriteRead(Set<Location> listOfBlocks, String filename) throws IOException {
        this.file = new File(filename);
        this.locationSet = listOfBlocks;
    }

    public void fileRead() throws IOException {
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            ArrayList<Integer> digitList = new ArrayList<>();
            ArrayList<Integer> listOfNumbers = new ArrayList<>();
            for (char x : input.toCharArray()) {

                if (x == ' ' || x == ';') {

                    Collections.reverse(digitList);
                    int number = 0;
                    int times = 1;

                    for (int y : digitList) {
                        number += y * times;
                        times *= 10;
                    }

                    listOfNumbers.add(number);
                    digitList.clear();

                } else {
                    digitList.add(Integer.parseInt(String.valueOf(x)));
                }
            }
            locationSet.add(new Location(null,listOfNumbers.get(0),listOfNumbers.get(1),listOfNumbers.get(2)));
        }
        scanner.close();
    }

    public void fileWrite() throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        for (Location x : locationSet){
            String string = x.getX()+" "+x.getY()+" "+x.getZ()+";\n";
            fileWriter.write(string);
        }
    }
}