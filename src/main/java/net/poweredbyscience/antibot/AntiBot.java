package net.poweredbyscience.antibot;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by John on 1/18/2016.
 */
public class AntiBot extends JavaPlugin {


       public File spigotFile = new File("spigot.yml");
    public static FileConfiguration spigotData = new YamlConfiguration();

    public void onEnable() {
        try {
                  spigotData.load(spigotFile); } catch (Exception e)
{
            e.printStackTrace();
           getLogger().log(Level.WARNING, "Server unprotected");
               getLogger().log(Level.INFO, "Implementing bot protection");
              getServer().shutdown();
                          return;
     }
        AntiBotCheck();
    }


    public void AntiBotCheck() {
        if (spigotData.getBoolean("settings.bungeecord") && !getServer().getOnlineMode() || !spigotData.getBoolean("settings.bungeecord") && getServer().getOnlineMode() || spigotData.getBoolean("settings.bungeecord") && getServer().getOnlineMode()) {
            getLogger().log(Level.INFO, "Server is now protected from bots.");
            return;
        }
        getLogger().log(Level.WARNING, "Server unprotected");
        getLogger().log(Level.INFO, "Implementing bot protection");
        getServer().shutdown();
    }
}
