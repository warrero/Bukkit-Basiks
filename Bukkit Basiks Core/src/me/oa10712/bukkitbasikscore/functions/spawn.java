/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasikscore.functions;

import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 *
 * @author Oa10712
 */
public class spawn {

    static final Logger log = Logger.getLogger("Minecraft");

    public void spawn(Player player, String[] args) {
        if (player != null) {
            World w = player.getWorld();
            Location l = player.getLocation();
            int px = l.getBlockX();
            int py = l.getBlockY();
            int pz = l.getBlockZ();
            if (args.length == 0) {
                if (player == null) {
                    log.info("This command cannot be run from the console without more arguments");
                } else {
                    player.teleport(w.getSpawnLocation());
                    player.sendMessage("Teleported to spawn");
                }
            } else {
                if (args[0].equalsIgnoreCase("set")) {
                    w.setSpawnLocation(px, py, pz);
                    player.sendMessage("Spawn for world '" + w.getName() + "' set");
                }
            }
        } else {
            log.info("This cannot be run from the cosole");
        }
    }
}
