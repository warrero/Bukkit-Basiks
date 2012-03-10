/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Oa10712
 */
public class BukkitBasiksMagick extends JavaPlugin {

    Logger log = Logger.getLogger("Minecraft");
    static Server server = Bukkit.getServer();
    private YamlConfiguration magickConfig;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File magickConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");
    private YamlConfiguration monsterType;

    @Override
    public void onEnable() {
        try {
            magickConfig = new YamlConfiguration();
            magickConfig.load(magickConfigFile);
            if (magickConfig.contains("Magick.Enabled")) {
            }
            magickConfig.set("Magick.Enabled", true);
            magickConfig.save(magickConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDisable() {
        try {
            magickConfig = new YamlConfiguration();
            magickConfig.load(magickConfigFile);
            magickConfig.set("Magick.Enabled", false);
            magickConfig.save(magickConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (cmd.getName().equalsIgnoreCase("hunger")) {
            if (player != null) {
                Player pHurt = server.getPlayer(args[0]);
                pHurt.setFoodLevel(2);
                player.sendMessage("Set" + pHurt + "hunger Level to 2");
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("summon")) {
            World w = player.getWorld();

            if (player != null) {
                EntityType m = EntityType.valueOf(args[0].toUpperCase());

                if (args[0].equals("creeper")) {
                    w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                } else if (args[0].equals("spider")) {
                    w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                } else if (args[0].equals("skeleton")) {
                    w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                } else{
                    player.sendMessage("Invalid Command");
                }                   
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("conjour")) {
            
            if (player != null) {
                ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                if(args[0].equals("diamond")){
                    player.sendMessage("You're chance of diamond = 0%");
                } else if (args[0].equals("apple")){
                    player.sendMessage("You're chance of apple = 80%");
                } else if (args[0].equals("pork")){
                    player.sendMessage("You're chance of pork = 65%");
                } else if (args[0].equals("steak")){
                    player.sendMessage("You're chance of steak = 50%");
                } else{
                    player.sendMessage("INVALID ITEM USE /CITEM");
                }
            }
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("citem")){
            
            if(player != null){
               player.sendMessage("The items you can conjour are: Diamond, Apple, Pork, Steak");
            }else{
                log.info("Must be logged in to use this command.");
            }
            return true;
        }

        return false;
    }
}
