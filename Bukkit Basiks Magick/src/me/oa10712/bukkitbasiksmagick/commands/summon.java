/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick.commands;

import java.io.File;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksmagick.BukkitBasiksMagick;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Oa10712
 */
public class summon implements CommandExecutor {
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
            World w = player.getWorld();

            if (player != null) {
                EntityType m = EntityType.valueOf(args[0].toUpperCase());
                PlayerInventory inventory = player.getInventory();
                ItemStack dirt = new ItemStack(Material.DIRT, 20);
                ItemStack gunpowder = new ItemStack(Material.SULPHUR, 1);
                ItemStack bone = new ItemStack(Material.BONE, 1);
                ItemStack rFlesh = new ItemStack(Material.ROTTEN_FLESH, 1);
                ItemStack string = new ItemStack(Material.STRING, 1);
                ItemStack pork = new ItemStack(Material.PORK, 1);
                ItemStack feather = new ItemStack(Material.FEATHER, 1);
                ItemStack hide = new ItemStack(Material.LEATHER, 1);
                ItemStack wool = new ItemStack(Material.WOOL, 2);

                if (inventory.contains(dirt)) {
                    inventory.remove(dirt);
                    if (args[0].equals("creeper")) {
                        if (inventory.contains(gunpowder)) {
                            inventory.remove(gunpowder);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have Sulphur!");
                        }
                    } else if (args[0].equals("spider")) {
                        if (inventory.contains(string)) {
                            inventory.remove(string);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have String!");
                        }
                    } else if (args[0].equals("skeleton")) {
                        if (inventory.contains(bone)) {
                            inventory.remove(bone);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have a Bone");
                        }
                    } else if (args[0].equals("zombie")) {
                        if (inventory.contains(rFlesh)) {
                            inventory.remove(rFlesh);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have Rotten Flesh");
                        }
                    } else if (args[0].equals("pig")) {
                        if (inventory.contains(pork)) {
                            inventory.remove(pork);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have Pork");
                        }
                    } else if (args[0].equals("cow")) {
                        if (inventory.contains(hide)) {
                            inventory.remove(hide);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have Leather");
                        }
                    } else if (args[0].equals("chicken")) {
                        if (inventory.contains(feather)) {
                            inventory.remove(feather);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have A Feather.");
                        }
                    } else if (args[0].equals("sheep")) {
                        if (inventory.contains(wool)) {
                            inventory.remove(wool);
                            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m);
                        } else {
                            player.sendMessage("You Need To Have More Wool.");
                        }

                    } else {
                        player.sendMessage("Invalid Command");
                    }
                } else {
                    player.sendMessage("You must have the materials to summon monsters! use /sitems [monster] to find the item you need.");
                }
            }
        } else {
            log.info("This cannot be run from the cosole");
        }
        return true;
    }
}
