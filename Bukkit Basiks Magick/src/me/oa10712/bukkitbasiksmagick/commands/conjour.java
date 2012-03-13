/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick.commands;

import java.io.File;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksmagick.BukkitBasiksMagick;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Oa10712
 */
public class conjour implements CommandExecutor {
    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksMagick.instance.getServer();
    private YamlConfiguration userData;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player != null) {
            if (player != null) {
                ItemStack i = new ItemStack(Material.getMaterial(args[0].toUpperCase()));
                int number = generateNumber(1, 100);
                PlayerInventory inventory = player.getInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND, 1);
                ItemStack apple = new ItemStack(Material.APPLE, 1);
                ItemStack pork = new ItemStack(Material.PORK, 1);
                ItemStack steak = new ItemStack(Material.COOKED_BEEF, 1);
                ItemStack dirt = new ItemStack(Material.DIRT, 1);


                if (inventory.contains(Material.DIRT)) {
                    inventory.remove(dirt);
                    if (args[0].equals("diamond")) {
                        player.sendMessage("You're chance of diamond = 2.5%");
                        if (number <= 2.5) {
                            inventory.addItem(diamond);
                            player.sendMessage(ChatColor.GREEN + "Congratz! You conjoured a Diamond!!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You were not able to conjour a diamond");
                        }
                    } else if (args[0].equals("apple")) {
                        player.sendMessage("You're chance of apple = 80%");
                        if (number <= 80) {
                            inventory.addItem(apple);
                            player.sendMessage(ChatColor.GREEN + "You conjoured an apple!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You were not able to conjour any apples.");
                        }
                    } else if (args[0].equals("pork")) {
                        player.sendMessage("You're chance of pork = 65%");
                        if (number <= 65) {
                            inventory.addItem(pork);
                            player.sendMessage(ChatColor.GREEN + "You conjoured some pork!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You were not able to conjour any pork.");
                        }
                    } else if (args[0].equals("STEAK")) {
                        player.sendMessage("You're chance of steak = 50%");
                        if (number <= 50) {
                            inventory.addItem(steak);
                            player.sendMessage(ChatColor.GREEN + "You conjoured some steak!");
                        } else {
                            player.sendMessage(ChatColor.RED + "You were not able to conjour any steak.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "INVALID ITEM USE /CITEM");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "It costs 1 dirt to attempt to conjour items");
                }
            }
        } else {
            log.info("This cannot be run from the cosole");
        }
        return true;
    }
}
