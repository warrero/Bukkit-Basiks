package me.oa10712.bukkitbasikscore;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasikscore.functions.getPermission;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Oa10712
 */
public class BukkitBasiksCore extends JavaPlugin implements Listener {

    static final Logger log = Logger.getLogger("Minecraft");
    static Server server = Bukkit.getServer();
    private YamlConfiguration basiksConfig;
    private YamlConfiguration userData;
    public transient getPermission perm;
    File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    File basiksConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");
    public static Plugin instance;

    public File getdata() {
        return dataFolder;
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("spawn").setExecutor(new commands());
        getCommand("assassinate").setExecutor(new commands());
        basiksConfig = new YamlConfiguration();
        if (basiksConfigFile.exists() == false) {
            try {
                String[] extensions = {"Worlds", "Teleports", "Economy", "Fun", "Magick"};
                basiksConfig = new YamlConfiguration();
                basiksConfig.set("Version", this.getDescription().getVersion());
                basiksConfig.set("Settings.Tree Fall", true);
                basiksConfig.set("Extensions", extensions);
                basiksConfig.save(basiksConfigFile);
                log.info("World config generated");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (userDataFile.exists() == false) {
            try {
                userData = new YamlConfiguration();
                userData.save(userDataFile);
            } catch (IOException ex) {
                Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        server.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void normalLogin(PlayerLoginEvent event) {
        try {
            userData = new YamlConfiguration();
            Player player = event.getPlayer();
            userData.load(userDataFile);
            userData.set("Users." + player.getName() + ".Last Login", System.currentTimeMillis());
            userData.save(userDataFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksCore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.LOG) {
            treeDeLog(block);
        }
    }

    //Functions
    private void treeDeLog(Block block) {
        block.breakNaturally();
        for (BlockFace face : BlockFace.values()) {
            Block next = block.getRelative(face);
            if (next.getType() == Material.LOG) {
                treeDeLog(next);
            }
        }
    }

}
