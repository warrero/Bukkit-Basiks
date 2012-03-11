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
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
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
                PlayerInventory inventory = player.getInventory();
                ItemStack dirt = new ItemStack(Material.DIRT, 20);
                ItemStack gunpowder =  new ItemStack(Material.SULPHUR, 1);
                ItemStack bone =  new ItemStack(Material.BONE, 1);
                ItemStack rFlesh =  new ItemStack(Material.ROTTEN_FLESH, 1);
                ItemStack string =  new ItemStack(Material.STRING, 1);
                ItemStack pork =  new ItemStack(Material.PORK, 1);
                ItemStack feather =  new ItemStack(Material.FEATHER, 1);
                ItemStack hide =  new ItemStack(Material.LEATHER, 1);
                ItemStack wool =  new ItemStack(Material.WOOL, 2);
                
                if(inventory.contains(dirt)){
                inventory.remove(dirt);
                    if (args[0].equals("creeper")) {
                        if(inventory.contains(gunpowder)){
                            inventory.remove(gunpowder);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have Sulphur!");
                        }
                    } else if (args[0].equals("spider")) {
                        if(inventory.contains(string)){
                            inventory.remove(string);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have String!");
                        }
                    } else if (args[0].equals("skeleton")) {
                        if(inventory.contains(bone)){
                            inventory.remove(bone);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have a Bone");
                        }
                    } else if (args[0].equals("zombie")) {
                        if(inventory.contains(rFlesh)){
                            inventory.remove(rFlesh);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have Rotten Flesh");
                        }
                    } else if (args[0].equals("pig")) {
                        if(inventory.contains(pork)){
                            inventory.remove(pork);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have Pork");
                        }
                    } else if (args[0].equals("cow")) {
                        if(inventory.contains(hide)){
                            inventory.remove(hide);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);    
                        }else{
                            player.sendMessage("You Need To Have Leather");
                        }
                    } else if (args[0].equals("chicken")) {
                        if(inventory.contains(feather)){
                            inventory.remove(feather);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have A Feather.");
                        }
                    } else if (args[0].equals("sheep")) {
                        if(inventory.contains(wool)){
                            inventory.remove(wool);
                        w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        }else{
                            player.sendMessage("You Need To Have More Wool.");
                        }
                
                }else{
                    player.sendMessage("Invalid Command");
                }                   
            }else{
                    player.sendMessage("You must have the materials to summon monsters! use /sitems [monster] to find the item you need.");
                }
            }
            return true;
        }
      
        

        if (cmd.getName().equalsIgnoreCase("conjour")) {
            
            if (player != null) {
                ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                int number = generateNumber(1,100);
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
                ItemStack apple = new ItemStack(Material.APPLE, 1);
                ItemStack pork = new ItemStack(Material.PORK, 1);
                ItemStack steak = new ItemStack(Material.COOKED_BEEF, 1);
                ItemStack dirt = new ItemStack(Material.DIRT, 1);
               
                
               if(inventory.contains(Material.DIRT)){  
                   inventory.remove(dirt);
                    if(args[0].equals("diamond")){
                        player.sendMessage("You're chance of diamond = 2.5%");                  
                        if(number <= 2.5){
                            inventory.addItem(diamond);
                            player.sendMessage(ChatColor.GREEN +"Congratz! You conjoured a Diamond!!");
                        }else{
                            player.sendMessage(ChatColor.RED +"You were not able to conjour a diamond");
                        }
                    } else if (args[0].equals("apple")){
                        player.sendMessage("You're chance of apple = 80%");
                        if(number <= 80){
                            inventory.addItem(apple);
                            player.sendMessage(ChatColor.GREEN +"You conjoured an apple!");
                        }else{
                            player.sendMessage(ChatColor.RED +"You were not able to conjour any apples.");
                        }
                    } else if (args[0].equals("pork")){
                        player.sendMessage("You're chance of pork = 65%");
                        if(number <= 65){
                            inventory.addItem(pork);
                            player.sendMessage(ChatColor.GREEN +"You conjoured some pork!");
                        }else{
                            player.sendMessage(ChatColor.RED +"You were not able to conjour any pork.");
                        }          
                    } else if (args[0].equals("STEAK")){
                        player.sendMessage("You're chance of steak = 50%");
                        if(number <= 50){
                            inventory.addItem(steak);
                            player.sendMessage(ChatColor.GREEN +"You conjoured some steak!");
                        }else{
                            player.sendMessage(ChatColor.RED +"You were not able to conjour any steak.");
                        }
                    } else{
                        player.sendMessage(ChatColor.RED + "INVALID ITEM USE /CITEM");
                    }
               }else{
                   player.sendMessage(ChatColor.RED +"It costs 1 dirt to attempt to conjour items");
               }
            }
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("citem")){
            
            if(player != null){
               player.sendMessage(ChatColor.DARK_RED +"The items you can conjour are: Diamond, Apple, Pork, Steak");
            }else{
                log.info("Must be logged in to use this command.");
            }
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("fireball")){
            
        }
        
        if(cmd.getName().equalsIgnoreCase("pick")){
            if(player != null){
            ItemStack pick = new ItemStack(Material.WOOD_PICKAXE, 1);
            PlayerInventory inventory = player.getInventory();
            short durability = 1;
            
            inventory.addItem(pick);
            pick.setDurability(durability);
            pick.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            pick.addEnchantment(Enchantment.DIG_SPEED, 5);
            }
            return true;
        }
        
        if(cmd.getName().equalsIgnoreCase("axe")){
            if(player!= null){
            ItemStack axe = new ItemStack(Material.WOOD_AXE, 1);
            PlayerInventory inventory = player.getInventory();
            short durability = 5; 
            
            inventory.addItem(axe);
            axe.setDurability(durability);
            axe.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            axe.addEnchantment(Enchantment.DIG_SPEED, 5);          
            }
        }

        return false;
    }
    
    public int generateNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
    /*
    public static void removeInventoryItems(Inventory inv, ItemStack item) {
        removeInventoryItems(inv, item.getType(), item.getAmount());
    }
    public static void removeInventoryItems(Inventory inv, Material type, int amount) {
        for (ItemStack is : inv.getContents()) {
            if (is != null && is.getType() == type) {
                int newamount = is.getAmount() - amount;
                if (newamount > 0) {
                    is.setAmount(newamount);
                    break;
                } else {
                    inv.remove(is);
                    amount = -newamount;
                    if (amount == 0) break;
                }
            }
        }
    }
     */
    
}
