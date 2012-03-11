package me.oa10712.bukkitbasiksenonomy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.oa10712.bukkitbasikscore.BukkitBasiksCore;
import me.oa10712.bukkitbasikscore.functions.setConfig;
import me.oa10712.bukkitbasiksenonomy.commands.balance;
import me.oa10712.bukkitbasiksenonomy.commands.buy;
import me.oa10712.bukkitbasiksenonomy.commands.pay;
import me.oa10712.bukkitbasiksenonomy.commands.sell;
import me.oa10712.bukkitbasiksenonomy.commands.worth;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BukkitBasiksEconomy extends JavaPlugin implements Listener {

    static final Logger log = Logger.getLogger("Minecraft");
    public static Plugin instance;
    Server server = Bukkit.getServer();
    private YamlConfiguration economyConfig;
    private YamlConfiguration worth;
    protected setConfig configset;
    public File dataFolder = new File(server.getWorldContainer().getPath() + File.separator + "plugins" + File.separator + "Bukkit Basiks");
    public File economyConfigFile = new File(dataFolder.getPath() + File.separator + "bukkitbasiksconfig.yml");
    public File worthFile = new File(dataFolder.getPath() + File.separator + "cost.yml");
    private YamlConfiguration userData;
    File userDataFile = new File(dataFolder.getPath() + File.separator + "userData.yml");

    @Override
    public void onDisable() {
        try {
            economyConfig = new YamlConfiguration();
            economyConfig.load(economyConfigFile);
            economyConfig.set("Economy.Enabled", false);
            economyConfig.save(economyConfigFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onEnable() {
        instance = this;
        getCommand("worth").setExecutor(new worth());
        getCommand("balance").setExecutor(new balance());
        getCommand("buy").setExecutor(new buy());
        getCommand("sell").setExecutor(new sell());
        getCommand("pay").setExecutor(new pay());
        try {
            economyConfig = new YamlConfiguration();
            economyConfig.load(economyConfigFile);
            if ((economyConfig.contains("Economy.Sell/Buy_Rate") == false)) {
                setupeconomyConfig();
            }
            if (!worthFile.exists()) {
                setupworth();
            }
            economyConfig.load(economyConfigFile);
            economyConfig.set("Economy.Enabled", true);
            economyConfig.save(economyConfigFile);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        }

        server.getPluginManager().registerEvents(this, this);
    }

    private void setupeconomyConfig() {
        try {
            economyConfig = new YamlConfiguration();
            economyConfig.load(economyConfigFile);
            economyConfig.set("Economy.Sell/Buy_Rate", 1.5);
            economyConfig.save(economyConfigFile);
            log.info("Economy config generated");


        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupworth() {
        try {
            worth = new YamlConfiguration();
            worth.load(this.getResource("cost.yml"));
            worth.save(worthFile);
            log.info("Worth file generated");


        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public void normalLogin(PlayerLoginEvent event) {
        try {
            userData = new YamlConfiguration();
            Player player = event.getPlayer();
            userData.load(userDataFile);
            if (userData.contains("Users." + player.getName() + ".Money") == false) {
                userData.set("Users." + player.getName() + ".Money", 100.0);
            }
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
    public void playerClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block block = event.getClickedBlock();
            BlockState state = block.getState();
            if (block.getType() == Material.SIGN_POST) {
                Sign sign = (Sign) state;

                signAction(event.getPlayer(), sign);
            }
            if (block.getType() == Material.WALL_SIGN) {
                Sign sign = (Sign) state;
                signAction(event.getPlayer(), sign);
            }

        }
    }

    @EventHandler
    public void signPlace(SignChangeEvent event) {
        event.getPlayer().sendMessage("Sign placed");
        String[] lines = event.getLines();
        String firstline = null;
        //<editor-fold defaultstate="collapsed" desc="heal">
        if (lines[0].equalsIgnoreCase("[heal]")) {
            //Double cost = convertCash(lines[1]);
            try {
                Double.parseDouble(lines[1].replace("$", ""));
                if (lines[2].equalsIgnoreCase("full") || lines[2].equalsIgnoreCase("full heal")) {
                    firstline = "§a" + lines[0];
                } else {
                    firstline = "§4" + lines[0];
                }
            } catch (NumberFormatException nfe) {
                firstline = "§4" + lines[0];
            }/*
            if (cost.isNaN()) {
            firstline = "§4" + lines[0];
            } else {
            if (lines[2].equalsIgnoreCase("full") || lines[2].equalsIgnoreCase("full heal")) {
            firstline = "§a" + lines[0];
            } else {
            firstline = "§4" + lines[0];
            }
            }*/
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="spawnmob">
        } else if (lines[0].equalsIgnoreCase("[spawnmob]")) {
            Double cost = convertCash(lines[3]);
            Double number = Double.valueOf(lines[1]);
            EntityType type = EntityType.valueOf(lines[2].toUpperCase());
            if (cost.isNaN()) {
                firstline = "§4" + lines[0];
            } else {
                if (number.isNaN()) {
                    firstline = "§4" + lines[0];
                } else {
                    if (type.isSpawnable()) {
                        firstline = "§a" + lines[0];
                    } else {
                        firstline = "§4" + lines[0];
                    }
                }
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="scroll">
        } else if (lines[0].equalsIgnoreCase("[scroll]")) {
            firstline = "§k" + "abcdefghijklmno";
            event.setLine(1, firstline);
            event.setLine(2, firstline);
            event.setLine(3, firstline);

            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="trade">
        } else if (lines[0].equalsIgnoreCase("[trade]")) {
            Double cost = convertCash(lines[2]);
            String[] itemdata = lines[1].split(":");
            //int number = Integer.getInteger(itemdata[1]);
            String item = getItem(itemdata[0]);
            Material[] materials = Material.values();
            if (cost.isNaN()) {
                firstline = "§4" + lines[0];
            } else {
                Boolean validitem = false;
                for (int i = 0; i < materials.length; i++) {
                    if (Material.valueOf(item.toUpperCase()).equals(materials[i])) {
                        validitem = true;
                    }
                }
                if (validitem) {
                    event.setLine(1, Material.valueOf(item.toUpperCase()).name() + ":");
                    firstline = "§a" + lines[0];
                    event.setLine(3, event.getPlayer().getDisplayName());
                } else {
                    firstline = "§4" + lines[0];
                }
            }
        }
        //</editor-fold>
        event.setLine(0, firstline);
    }

    private String removeSpace(String name) {
        String[] seperate = name.split("_");
        String temp1 = null;
        for (int i = 0; i < seperate.length; i++) {
            if (i == 0) {
                temp1 = seperate[i];
            } else {
                temp1 = temp1 + seperate[i];
            }
        }
        return temp1;
    }

    public String[] splitItem(String string) {
        String[] splitted = string.split(":");
        return splitted;
    }

    private void signAction(Player player, Sign sign) {
        try {
            String[] text = sign.getLines();
            userData = new YamlConfiguration();
            userData.load(userDataFile);
            double curcash = userData.getDouble("Users." + player.getName() + ".Money");
            double changecash = 0.0;
            if (text[0].equalsIgnoreCase("[heal]") || text[0].equalsIgnoreCase("§a[heal]")) {
                changecash = convertCash(text[1]);
                if (changecash > curcash) {
                    player.sendMessage("You cannot afford this");
                } else {
                    if (text[2].equalsIgnoreCase("full") || text[2].equalsIgnoreCase("full heal")) {
                        player.setHealth(20);
                        userData.set("Users." + player.getName() + ".Money", curcash - changecash);
                        player.sendMessage("Healed for §6$" + changecash);
                    } else {
                        player.setHealth(player.getHealth() + Integer.parseInt(text[2]));
                        userData.set("Users." + player.getName() + ".Money", curcash - changecash);
                        player.sendMessage("Healed for §6$" + changecash);
                    }
                }
                userData.save(userDataFile);
            }
            if (text[0].equalsIgnoreCase("[spawnmob]") || text[0].equalsIgnoreCase("§a[spawnmob]")) {
                changecash = convertCash(text[3]);
                if (changecash > curcash) {
                    player.sendMessage("You cannot afford this");
                } else {
                    Location spawnlocat = sign.getBlock().getLocation();
                    EntityType et = EntityType.valueOf(text[2].toUpperCase());
                    userData.set("Users." + player.getName() + ".Money", curcash - changecash);
                    userData.save(userDataFile);
                    player.sendMessage("Spawned " + text[1] + " " + text[2] + "(s) for §6$" + changecash);
                    for (int i = 0; i < Integer.parseInt(text[1]); i++) {
                        player.getWorld().spawnCreature(spawnlocat, et);
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidConfigurationException ex) {
            Logger.getLogger(BukkitBasiksEconomy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private double convertCash(String string) {
        if (string.contains(".")) {
            return Double.valueOf(string.replace("$", ""));
        } else {
            return Double.valueOf(string.replace("$", "") + ".00");
        }
    }

    private String getItem(String string) {
        String item = null;
        if (Double.valueOf(string).isNaN()) {
            item = string.toUpperCase();
        } else {
            int itemnum = Integer.getInteger(string);
            item = Material.getMaterial(itemnum).name().toLowerCase();
        }
        return item;
    }
}
