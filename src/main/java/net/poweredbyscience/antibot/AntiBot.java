package net.poweredbyscience.antibot;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import java.util.logging.Level;
import java.util.function.*;

/*
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

/*
 * Created by John on 1/18/2016.
 */
 
public class AntiBot extends JavaPlugin {
    
    @Deprecated // magic value
    public static final SPIGOT_CONFIG_FILE = "spigot.yml";
    
    // TODO: abstract some more
    BotCheckProviderFactory factory = new BotCheckProviderFactory(() -> {
        return () -> {
            return () -> {
                if (spigotData.getBoolean("settings.bungeecord") && !getServer().getOnlineMode()
                        || !spigotData.getBoolean("settings.bungeecord") && getServer().getOnlineMode()
                        || spigotData.getBoolean("settings.bungeecord") && getServer().getOnlineMode()) {
                    getLogger().log(Level.INFO, "Server is now protected from bots.");
                    return;
                }
                getLogger().log(Level.WARNING, "Server unprotected");
                getLogger().log(Level.INFO, "Implementing bot protection");
                getServer().shutdown();
            }
        }
    });
    
    @FunctionalInterface
    public interface BotCheckProvider {
        public void antiBotCheck();
    }
    
    public static class BotCheckProviderFactory {
        Supplier<BotCheckProvider> supplier;
        BotCheckProviderFactory(Supplier<BotCheckProvider> supplier) {
            this.supplier = supplier;
        }
        BotCheckProvider build() {
            return supplier.get();
        }
    }
    
    // TODO: fix scalabily issues - what if the config file is yavascript in a newer version?
    private final File spigotFile = new File(SPIGOT_CONFIG_FILE);
    private final FileConfiguration spigotData = new YamlConfiguration();
    
    @SupressWarnings("deprecation")
    public void onEnable() {
        try {
            spigotData.load(spigotFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        factory.build().antiBotCheck();
    }
}
