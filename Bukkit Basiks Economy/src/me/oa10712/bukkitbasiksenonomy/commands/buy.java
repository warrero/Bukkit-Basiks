/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksenonomy.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksenonomy.BukkitBasiksEconomy;
import me.oa10712.bukkitbasiksenonomy.functions.convert;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Oa10712
 */
public class buy implements CommandExecutor {

    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksEconomy.instance.getServer();
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

            try {
                userData = new YamlConfiguration();
                userData.load(userDataFile);
                double curcash = userData.getDouble("Users." + player.getName() + ".Money");
                if (args.length == 2) {
                    double quantityrate = 0;
                    String[] itemdata = splitItem(args[0]);
                    if (itemdata.length == 2) {
                        quantityrate = convert.buyPrice(itemdata[0], Integer.valueOf(itemdata[1]));
                    } else {
                        quantityrate = convert.buyPrice(args[0].toLowerCase());
                    }
                    double changecash = quantityrate * Double.valueOf(args[1]);
                    if (changecash != 0.0) {
                        if (changecash > curcash) {
                            player.sendMessage("You cannot afford this many of this item");
                        } else {
                            userData.set("Users." + player.getName() + ".Money", curcash - changecash);
                            userData.save(userDataFile);
                            player.sendMessage("Bought " + args[1] + " " + args[0] + "(s) for §6$" + changecash);
                            ItemStack bought;
                            if (itemdata.length == 1) {
                                bought = new ItemStack(Material.getMaterial(args[0].toUpperCase()), Integer.valueOf(args[1]));
                            } else {
                                bought = new ItemStack(Material.getMaterial(itemdata[0].toUpperCase()), Integer.parseInt(args[1]), Short.parseShort(itemdata[1]));
                            }
                            player.getInventory().addItem(bought);
                        }
                    } else {
                        player.sendMessage("This item cannot be bought from the server");
                    }
                } else if (args.length == 1) {
                    double changecash;
                    String[] itemdata = splitItem(args[0]);
                    if (itemdata.length == 2) {
                        changecash = convert.buyPrice(itemdata[0], Integer.valueOf(itemdata[1]));
                    } else {
                        changecash = convert.buyPrice(args[0].toLowerCase());
                    }
                    if (changecash != 0.0) {
                        if (changecash > curcash) {
                            player.sendMessage("You cannot afford this many of this item");
                        } else {
                            userData.set("Users." + player.getName() + ".Money", curcash - changecash);
                            userData.save(userDataFile);
                            player.sendMessage("Bought 1 " + args[0] + "(s) for §6$" + changecash);
                            ItemStack bought;
                            if (itemdata.length == 1) {
                                bought = new ItemStack(Material.getMaterial(args[0].toUpperCase()), 1);
                            } else {
                                bought = new ItemStack(Material.getMaterial(itemdata[0].toUpperCase()), 1, Short.parseShort(itemdata[1]));
                            }
                            player.getInventory().addItem(bought);
                        }
                    } else {
                        player.sendMessage("This item cannot be bought from the server");
                    }
                }
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.info("This cannot be run from the cosole");
        }
        return true;
    }

    public String[] splitItem(String string) {
        String[] splitted = string.split(":");
        return splitted;
    }
}
