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
public class pay implements CommandExecutor {

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
                Double transfer = Double.valueOf(args[1]);
                if (transfer > 0.00) {
                    userData = new YamlConfiguration();
                    userData.load(userDataFile);
                    Double playermoney = userData.getDouble("Users." + player.getName() + ".Money");
                    Player recieve = server.getPlayer(args[0]);
                    if (userData.contains("Users." + args[0] + ".Money")) {
                        Double recievemoney = userData.getDouble("Users." + args + ".Money");
                        userData.set("Users." + args[0] + ".Money", recievemoney + transfer);
                        userData.set("Users." + player.getName() + ".Money", playermoney - transfer);
                        userData.save(userDataFile);
                        player.sendMessage("Paid $" + transfer + " to " + recieve.getName());
                        recieve.sendMessage("Recieved $" + transfer + " from " + player.getName());
                    } else {
                        player.sendMessage("Invalid target");
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
}
