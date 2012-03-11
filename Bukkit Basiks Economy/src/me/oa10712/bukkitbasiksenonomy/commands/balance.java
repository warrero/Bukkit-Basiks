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
import me.oa10712.bukkitbasikscore.BukkitBasiksCore;
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
public class balance implements CommandExecutor {

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
                server = BukkitBasiksEconomy.instance.getServer();
                userData = new YamlConfiguration();
                userData.load(userDataFile);
                player.sendMessage("Current Ballance: §6$" + String.valueOf(userData.getDouble("Users." + player.getName() + ".Money")));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.info("This cannot be run from the console");
        }
        return true;
    }
}
