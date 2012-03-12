/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasikscore;

import java.io.File;
import java.util.logging.Logger;
import me.oa10712.bukkitbasikscore.BukkitBasiksCore;
import me.oa10712.bukkitbasikscore.functions.assassinate;
import me.oa10712.bukkitbasikscore.functions.spawn;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Oa10712
 */
public class commands implements CommandExecutor {

    public transient spawn spawn;
    public transient assassinate assassinate;
    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksCore.instance.getServer();
    private YamlConfiguration userData;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (command.getName().equalsIgnoreCase("spawn")) {
            spawn.spawn(player, args);
        }
        if (command.getName().equalsIgnoreCase("assassinate")) {
            assassinate.assassinate(player, args);
        }
        return true;
    }
}
