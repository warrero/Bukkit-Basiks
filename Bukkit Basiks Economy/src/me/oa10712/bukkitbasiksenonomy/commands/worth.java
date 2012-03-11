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

/**
 *
 * @author Oa10712
 */
public class worth implements CommandExecutor{

    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksEconomy.instance.getServer();
    private YamlConfiguration userData;
    private static YamlConfiguration economyConfig;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");
    File economyConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player != null) {
            try {
                economyConfig = new YamlConfiguration();
                economyConfig.load(economyConfigFile);
                double price = 0.0;
                String name = null;
                if (args.length == 1) {
                    price = convert.getPrice(args[0]);
                    name = args[0];
                } else {
                    price = convert.getPrice(player.getItemInHand().getType().name().toLowerCase());
                    name = player.getItemInHand().getType().name().toLowerCase();
                }
                if (price == 0.0) {
                    player.sendMessage("This item cannot be bought or sold to the server");
                } else {
                    double changecash = price * economyConfig.getDouble("Economy.Sell/Buy_Rate");
                    player.sendMessage("Price of 1 " + name + ": Buying §6$" + changecash + "§f, Selling §6$" + price);
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
}
