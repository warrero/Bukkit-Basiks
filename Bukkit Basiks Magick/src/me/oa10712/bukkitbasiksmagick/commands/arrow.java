/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

/**
 *
 * @author Dalton
 */
public class arrow implements CommandExecutor{
    
     @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
       
         Player player = null;
         EntityType m = EntityType.valueOf(args[0].toUpperCase());
        
         if (sender instanceof Player) {
            player = (Player) sender;
        }
        if (player != null) {
            World w = player.getWorld();
            w.spawnCreature(player.getTargetBlock(null, 0).getLocation(), m.ARROW);
        }      
        
         return false;
     }
    
}
