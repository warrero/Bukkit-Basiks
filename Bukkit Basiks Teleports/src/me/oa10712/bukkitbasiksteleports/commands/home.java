/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksteleports.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksteleports.BukkitBasiksTeleports;
import me.oa10712.bukkitbasiksteleports.functions.teleport;
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
public class home implements CommandExecutor {

    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksTeleports.instance.getServer();
    private YamlConfiguration userData;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");
    private transient teleport tele;

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
                double homex = userData.getDouble("Users." + player.getName() + ".Home.X");
                double homey = userData.getDouble("Users." + player.getName() + ".Home.Y");
                double homez = userData.getDouble("Users." + player.getName() + ".Home.Z");
                String homeworld = userData.getString("Users." + player.getName() + ".Home.World");
                Object[] argout = {command.getName()};
                if (homey != 0.0) {
                    tele.teleport(player, argout);
                } else {
                    player.sendMessage("You do not have a home yet. Use /sethome to set your home.");
                }
                return true;
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InvalidConfigurationException ex) {
                Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            log.info("This cannot be run from the cosole");
        }
        return true;
    }
}
