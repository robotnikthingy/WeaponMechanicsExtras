package com.gmail.robotnikman.WeaponMechanicsExtras;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class WeaponMechanicsExtras extends JavaPlugin {

    private WeaponMechanicsExtras plugin = this;

    private static Boolean  MythicMobsInstalled;

    static File PluginFolder;


    private static WeaponMechanicsExtras instance;

    public static ArrayList<WeaponMechanicsExtrasWeapon> Weapons = new ArrayList<WeaponMechanicsExtrasWeapon>();

    public void onEnable() {
        PluginFolder = getDataFolder();

        File subfolder = new File(getDataFolder(), "Weapons");

        if (!subfolder.exists()) {
            subfolder.mkdir();
        }


        //register listeners
        getServer().getPluginManager().registerEvents(new WeaponListener(this), this);


        // check and see if any softdependencies are installed
        if (getServer().getPluginManager().getPlugin("MythicMobs") != null) {
            getLogger().info("MythicMobs detected, hooked into MythicMobs!");
            MythicMobsInstalled = true;

        }

        loadWeapons();
    }

    public void loadWeapons(){

        ConfigurationSection WeaponsSect;
        ConfigurationSection ExplosivesSect;
        ConfigurationSection AttachmentsSect;

        //create weapon and config files if they dont exist
        File configFile = new File(PluginFolder + "/config.yml");

        File WeaponsFile = new File(PluginFolder + "/defaultWeapons.yml");
        File ExplosivesFile = new File(PluginFolder + "/defaultExplosives.yml");
        File AttachmentsFile = new File(PluginFolder + "/defaultAttachments.yml");

        if(!configFile.exists()){
            saveDefaultConfig();
        }

        //load weapons into memory
        File weaponsDir = new File(PluginFolder + "/Weapons");
        List<File> weaponfiles = getAllFiles(weaponsDir);

        for(File weaponFile:weaponfiles){
            FileConfiguration WeaponsFileConfig = YamlConfiguration.loadConfiguration(weaponFile);
            WeaponsSect = WeaponsFileConfig.getConfigurationSection("");

            for(String Weapon:WeaponsSect.getKeys(false)){
                getLogger().info("Loaded Weapon: " + Weapon);
                Weapons.add(new WeaponMechanicsExtrasWeapon(Weapon,WeaponsSect.getConfigurationSection(Weapon)));
            }

        }
    }

    // handles commands typed in game
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        Boolean IsPlayer = false;
        Player player = null;
        if ((sender instanceof Player)) {
            player = (Player) sender;
            IsPlayer = true;
        }

        if (cmd.getName().equalsIgnoreCase("WeaponMechanicsExtras") || cmd.getName().equalsIgnoreCase("WeaponMechanicsExtras")) {

            if (args.length == 0) {
                if (IsPlayer) {
                    player.sendMessage("Please enter a subcommand: WeaponMechanicsExtras <reload>");
                }

                getLogger().info("Please enter a subcommand: WeaponMechanicsExtras <reload>");

                return true;

                // there was a subcommand entered, lets see what it is
            } else {

                if (args[0].equalsIgnoreCase("reload")) {
                    //reload the configs and schematics
                    loadWeapons();

                    //send a message to the player if executed in game
                    if (IsPlayer) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2Plugin reloaded!"));
                    }
                }


            }
        }

        return true;
    }

    // used to copy files from the .jar file to the config folders
    void copy(InputStream in, File file) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<File> getAllFiles(File directory) {
        List<File> fileList = new ArrayList<>();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileList.addAll(getAllFiles(file));
                } else {
                    fileList.add(file);
                }
            }
        }

        return fileList;
    }

    public static Boolean isMythicMobsInstalled() {
        return MythicMobsInstalled;
    }

}
