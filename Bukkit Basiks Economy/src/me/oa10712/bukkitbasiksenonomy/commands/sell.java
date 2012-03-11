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
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Oa10712
 */
public class sell implements CommandExecutor {

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
                Double curcash = userData.getDouble("Users." + player.getName() + ".Money");
                Double changecash = Double.valueOf(0);
                if (args[0].equalsIgnoreCase("hand") || args.length == 0) {
                    ItemStack handitem = player.getItemInHand();
                    if (convert.metadata(handitem.getType().getId())) {
                        changecash = convert.getPrice(handitem.getType().name(), handitem.getDurability());
                        if (changecash != 0.0) {
                            userData.set("Users." + player.getName() + ".Money", curcash + changecash);
                            player.sendMessage("Sold " + String.valueOf(handitem.getAmount()) + " " + handitem.getType().name().toLowerCase()
                                    + "s of type " + String.valueOf(handitem.getDurability()) + " for §6$" + changecash);
                            handitem.setTypeId(0);
                            player.setItemInHand(handitem);
                        } else {
                            player.sendMessage("This item cannot be sold to the server");
                        }
                    } else {
                        changecash = convert.getPrice(handitem.getType().name()) * handitem.getAmount();
                        if (changecash != 0.0) {
                            userData.set("Users." + player.getName() + ".Money", curcash + changecash);
                            player.sendMessage("Sold " + String.valueOf(handitem.getAmount()) + " " + handitem.getType().name().toLowerCase()
                                    + "s for §6$" + changecash);
                            handitem.setTypeId(0);
                            player.setItemInHand(handitem);
                        } else {
                            player.sendMessage("This item cannot be sold to the server");
                        }
                    }
                } else if (args[0].equalsIgnoreCase("inventory") || args[0].equalsIgnoreCase("all")) {
                    PlayerInventory inv = player.getInventory();
                    double totalchange = 0.0;
                    ItemStack item;
                    for (int i = 0; i < 36; i++) {
                        item = inv.getItem(i);
                        userData.load(userDataFile);
                        if (item.getTypeId() == 0) {
                        } else {
                            if (convert.metadata(item.getType().getId())) {
                                changecash = convert.getPrice(item.getType().name(), item.getDurability());
                                if (changecash != 0.0) {
                                    userData.set("Users." + player.getName() + ".Money", curcash + changecash);
                                    item.setTypeId(0);
                                    inv.setItem(i, item);
                                    totalchange = totalchange + changecash;
                                } else {
                                }
                            } else {
                                changecash = convert.getPrice(item.getType().name()) * item.getAmount();
                                if (changecash != 0.0) {
                                    userData.set("Users." + player.getName() + ".Money", curcash + changecash);
                                    item.setTypeId(0);
                                    inv.setItem(i, item);
                                    totalchange = totalchange + changecash;
                                } else {
                                }
                            }
                        }
                        userData.save(userDataFile);
                    }
                    player.sendMessage("Sold all for $" + totalchange);
                }
                userData.save(userDataFile);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.info("This cannot be run from the Console");
        }
        return true;
    }
}
