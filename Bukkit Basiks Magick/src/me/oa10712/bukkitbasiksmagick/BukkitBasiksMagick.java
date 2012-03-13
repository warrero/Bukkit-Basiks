/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksmagick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasiksmagick.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Oa10712
 */
public class BukkitBasiksMagick extends JavaPlugin implements Listener {

    static final Logger log = Logger.getLogger("Minecraft");
    static Server server = Bukkit.getServer();
    private YamlConfiguration magickConfig;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File magickConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");
    private YamlConfiguration userData;
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");
    public static Plugin instance;

    @Override
    public void onEnable() {
        instance = this;
        getCommand("hunger").setExecutor(new hunger());
        getCommand("summon").setExecutor(new summon());
        getCommand("conjour").setExecutor(new conjour());
        getCommand("citem").setExecutor(new citem());
        getCommand("pick").setExecutor(new pick());
        getCommand("axe").setExecutor(new axe());
        try {
            magickConfig = new YamlConfiguration();
            magickConfig.load(magickConfigFile);
            if (magickConfig.contains("Magick.Enabled")) {
            }
            magickConfig.set("Magick.Enabled", true);
            magickConfig.save(magickConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDisable() {
        try {
            magickConfig = new YamlConfiguration();
            magickConfig.load(magickConfigFile);
            magickConfig.set("Magick.Enabled", false);
            magickConfig.save(magickConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

  
    public int generateNumber(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    @EventHandler
    public void normalLogin(PlayerLoginEvent event) {
        try {
            userData = new YamlConfiguration();
            Player player = event.getPlayer();
            userData.load(userDataFile);
            if (!userData.contains("Users." + player.getName() + ".Mana")) {
                userData.set("Users." + player.getName() + ".Mana", null);
                userData.save(userDataFile);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksMagick.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    /*
    public static void removeInventoryItems(Inventory inv, ItemStack item) {
    removeInventoryItems(inv, item.getType(), item.getAmount());
    }
    public static void removeInventoryItems(Inventory inv, Material type, int amount) {
    for (ItemStack is : inv.getContents()) {
    if (is != null && is.getType() == type) {
    int newamount = is.getAmount() - amount;
    if (newamount > 0) {
    is.setAmount(newamount);
    break;
    } else {
    inv.remove(is);
    amount = -newamount;
    if (amount == 0) break;
    }
    }
    }
    }
     */
    /* PlayerInventory inv = player.getInventory();
    HashMap<Integer, ? extends ItemStack> dirts = inv.all(Material.DIRT);
    int num = 0;
    int temp = 0;
    for (int i = 0; i < dirts.size(); i++) {
    ItemStack get = dirts.get(i);
    temp = num + get.getAmount();
    num = temp;
    }
    if (inv.contains(Material.DIRT)) {
    if (num < 10) {
    player.sendMessage("You do not have enough!");
    } else {
    ItemStack dirt = new ItemStack(Material.DIRT, num - 10);
    inv.addItem(dirt);
    }
    }*/
}
