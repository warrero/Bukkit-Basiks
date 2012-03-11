/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.oa10712.bukkitbasiksteleports;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasikscore.BukkitBasiksCore;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import me.oa10712.bukkitbasikscore.functions.setConfig;
import me.oa10712.bukkitbasiksteleports.commands.home;
import me.oa10712.bukkitbasiksteleports.commands.sethome;
import me.oa10712.bukkitbasiksteleports.commands.tp;
import me.oa10712.bukkitbasiksteleports.functions.teleport;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author Oa10712
 */
public class BukkitBasiksTeleports extends JavaPlugin implements Listener {

    private YamlConfiguration teleportsConfig;
    static final Logger log = Logger.getLogger("Minecraft");
    Server server = Bukkit.getServer();
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File teleportsConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");
    Plugin basiks = server.getPluginManager().getPlugin("Bukkit Basiks Core");
    private transient setConfig configset;
    private transient teleport tele;
    private YamlConfiguration userData;
    public static Plugin instance;
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");

    @Override
    public void onDisable() {
        try {
            teleportsConfig.load(teleportsConfigFile);
            teleportsConfig.set("Teleports.Enabled", false);
            teleportsConfig.save(teleportsConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("tp").setExecutor(new tp());
        getCommand("home").setExecutor(new home());
        getCommand("sethome").setExecutor(new sethome());
        teleportsConfig = new YamlConfiguration();
        this.configset = new setConfig();
        this.tele = new teleport();
        try {
            teleportsConfig.load(teleportsConfigFile);
            if ((teleportsConfig.contains("Teleports.Defaults") == false) || (teleportsConfig.getBoolean("Teleports.Default") == true)) {
                setupTeleportsConfig();
            }
            teleportsConfig.load(teleportsConfigFile);
            teleportsConfig.set("Teleports.Enabled", true);
            teleportsConfig.save(teleportsConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        }
        server.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void normalLogin(PlayerLoginEvent event) {
        try {
            userData = new YamlConfiguration();
            Player player = event.getPlayer();
            userData.load(userDataFile);
            if (userData.contains("Users." + player.getName() + ".Home")) {
                userData.set("Users." + player.getName() + ".Home", null);
                userData.save(userDataFile);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupTeleportsConfig() {
        try {
            teleportsConfig = new YamlConfiguration();
            teleportsConfig.load(teleportsConfigFile);
            teleportsConfig.set("Teleports.Defaults.Wait", 0);
            teleportsConfig.save(teleportsConfigFile);
            log.info("Teleports config generated");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksTeleports.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
