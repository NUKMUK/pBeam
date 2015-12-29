package me.NUKMUK;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class pBeams extends JavaPlugin implements Listener {

    private void startBeam() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!p.isDead() && !pl.isDead()) {
                            if (p.getLocation().distance(pl.getLocation()) >= getConfig().getInt("distance")) {

                                if (getConfig().getBoolean("cloud")) {
                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                            EnumParticle.CLOUD,
                                            true,
                                            pl.getLocation().getBlock().getX(),
                                            pl.getLocation().add(0.5, 10, 0.5).getBlock().getY(),
                                            pl.getLocation().getBlock().getZ(),
                                            1,
                                            10,
                                            1,
                                            0.05f,
                                            getConfig().getInt("particles"),
                                            null
                                    );
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                }

                                if (getConfig().getBoolean("fireworks_spark")) {
                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                            EnumParticle.FIREWORKS_SPARK,
                                            true,
                                            pl.getLocation().getBlock().getX(),
                                            pl.getLocation().add(0.5, 10, 0.5).getBlock().getY(),
                                            pl.getLocation().getBlock().getZ(),
                                            1,
                                            10,
                                            1,
                                            0.05f,
                                            getConfig().getInt("particles"),
                                            Integer.MAX_VALUE
                                    );
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                }
                            }
                        }
                    }
                }
            }

        }, 0, getConfig().getInt("delay"));

        if(getConfig().getBoolean("small")) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    for (Player pl : Bukkit.getOnlinePlayers()) {
                        if (!p.isDead() && !pl.isDead()) {
                            if (p.getLocation().distance(pl.getLocation()) >= getConfig().getInt("distance")) {

                                if (getConfig().getBoolean("small")) {
                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                            EnumParticle.REDSTONE,
                                            true,
                                            pl.getLocation().getBlock().getX(),
                                            pl.getLocation().add(0.5, 1, 0.5).getBlock().getY(),
                                            pl.getLocation().getBlock().getZ(),
                                            1,
                                            1,
                                            1,
                                            0.05f,
                                            getConfig().getInt("smallparticles"),
                                            Integer.MAX_VALUE
                                    );
                                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                                }
                            }
                        }
                    }
                }
            }
        }, 0, getConfig().getInt("delay") / 10);


    }
}

    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getServer().getPluginManager().registerEvents(this, this);

        startBeam();
    }
}