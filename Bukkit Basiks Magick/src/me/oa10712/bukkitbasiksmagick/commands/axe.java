/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick.commands;

import java.io.File;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksmagick.BukkitBasiksMagick;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 *
 * @author Oa10712
 */
public class axe implements CommandExecutor {

    static final Logger log = Logger.getLogger("Minecraft");
    Server server = BukkitBasiksMagick.instance.getServer();
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
            ItemStack axe = new ItemStack(Material.WOOD_AXE, 1);
            PlayerInventory inventory = player.getInventory();
            short durability = 50;

            axe.setDurability(durability);
            axe.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
            axe.addEnchantment(Enchantment.DIG_SPEED, 5);
            inventory.addItem(axe);
        } else {
            log.info("This cannot be run from the cosole");
        }
        return true;
    }
}
