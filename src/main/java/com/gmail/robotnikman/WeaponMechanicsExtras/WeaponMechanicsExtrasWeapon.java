package com.gmail.robotnikman.WeaponMechanicsExtras;

import org.bukkit.configuration.ConfigurationSection;

public class WeaponMechanicsExtrasWeapon {
    private String Name;

    private ConfigurationSection configSect;

    //list of mechanics the gun has
    Boolean MythicMobsSpawn;

    WeaponMechanicsExtrasWeapon(String Name, ConfigurationSection configSect){
        this.Name = Name;
        this.configSect = configSect;

    }


    String GetName(){
        return Name;
    }

    public ConfigurationSection getConfigSect() {
        return configSect;
    }

    //load properties of the weapon from crackshot and crackshot plus, etc
}
