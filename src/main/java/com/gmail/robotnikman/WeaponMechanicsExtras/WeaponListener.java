package com.gmail.robotnikman.WeaponMechanicsExtras;

import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import me.deecaad.weaponmechanics.weapon.weaponevents.ProjectileEndEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.ProjectileExplodeEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.ProjectileHitBlockEvent;
import me.deecaad.weaponmechanics.weapon.weaponevents.ProjectileHitEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;


public class WeaponListener implements Listener{
    Plugin plugin;

    WeaponListener(Plugin plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onProjectileEnd( ProjectileEndEvent event){

    }

    @EventHandler
    public void onProjectileExplode( ProjectileExplodeEvent event){

    }

    @EventHandler
    public void onProjectileHitBlock( ProjectileHitBlockEvent event){
        Bukkit.getServer().getConsoleSender().sendMessage("onProjectileHitBlock");
        String WeaponTitle = event.getWeaponTitle();

        //check if weapon exists in this plugin
        for(WeaponMechanicsExtrasWeapon Weapon: WeaponMechanicsExtras.Weapons){
            Bukkit.getServer().getConsoleSender().sendMessage("Weapon name = " + Weapon.GetName() + "    WeaponTitle = " + WeaponTitle);
            if(Weapon.GetName().equals(WeaponTitle)){
                //apply mechanics and do stuff
                if(Weapon.getConfigSect().contains("onWeaponHitBlock.SpawnMythicMob")){
                    String mobName = Weapon.getConfigSect().getString("onWeaponHitBlock.SpawnMythicMob");

                    MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobName).orElse(null);
                    Location spawnLocation = event.getHitLocation();

                    if(mob != null){
                        Bukkit.getServer().getConsoleSender().sendMessage("Spawning Mob: " + mobName);
                        // spawns mob
                        ActiveMob mythicMob = mob.spawn(BukkitAdapter.adapt(spawnLocation),1);

                        // get mob as bukkit entity
                        Entity entity = mythicMob.getEntity().getBukkitEntity();
                    }else{
                        Bukkit.getServer().getConsoleSender().sendMessage("Unable to find mob: " + mobName);
                    }


                }
            }
        }
    }

    @EventHandler
    public void onProjectileHitEntity(ProjectileHitEntityEvent event){
        Bukkit.getServer().getConsoleSender().sendMessage("onProjectileHitEntity");
        String WeaponTitle = event.getWeaponTitle();

        //check if weapon exists in this plugin
        for(WeaponMechanicsExtrasWeapon Weapon: WeaponMechanicsExtras.Weapons){
            Bukkit.getServer().getConsoleSender().sendMessage("Weapon name = " + Weapon.GetName() + "    WeaponTitle = " + WeaponTitle);
            if(Weapon.GetName().equals(WeaponTitle)){
                //apply mechanics and do stuff
                if(Weapon.getConfigSect().contains("onWeaponHitEntity.SpawnMythicMob")){
                    String mobName = Weapon.getConfigSect().getString("onWeaponHitBlock.SpawnMythicMob");

                    MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobName).orElse(null);
                    Location spawnLocation = event.getHitLocation();

                    if(mob != null){
                        Bukkit.getServer().getConsoleSender().sendMessage("Spawning Mob: " + mobName);
                        // spawns mob
                        ActiveMob mythicMob = mob.spawn(BukkitAdapter.adapt(spawnLocation),1);

                        // get mob as bukkit entity
                        Entity entity = mythicMob.getEntity().getBukkitEntity();
                    }else{
                        Bukkit.getServer().getConsoleSender().sendMessage("Unable to find mob: " + mobName);
                    }


                }
            }
        }
    }
}
