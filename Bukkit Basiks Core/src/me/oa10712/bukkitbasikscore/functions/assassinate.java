/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasikscore.functions;

import java.io.File;
import java.util.logging.Logger;
import me.oa10712.bukkitbasikscore.BukkitBasiksCore;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 *
 * @author Oa10712
 */
public class assassinate {

    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksCore.instance.getServer();
    private YamlConfiguration userData;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");

    public void assassinate(Player player, String[] args) {
        if (player != null) {

            if (server.getPlayer(args[0]).isOnline()) {
                Player target = server.getPlayer(args[0]);
                for (Entity attacker : target.getWorld().getEntities()) {
                    if (attacker instanceof Creature) {
                        Creature creature = (Creature) attacker;
                        creature.setTarget(target);
                    }
                }
            }

        } else {
            log.info("This cannot be run from the cosole");
        }
    }
}
