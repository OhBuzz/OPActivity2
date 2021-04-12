package net.buzz.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Config {
    private static final ConfigurationProvider provider = ConfigurationProvider.getProvider(YamlConfiguration.class);

    private final Plugin plugin;

    private final File file;

    private Configuration config;

    public Config(Plugin plugin) {
        this(plugin, "config.yml");
    }

    public Config(Plugin plugin, String name) {
        this(plugin, new File(plugin.getDataFolder(), name));
    }

    public Config(Plugin plugin, File file) {
        this.plugin = plugin;
        this.file = file;
        load();
    }

    public void load() {
        if (!this.file.getParentFile().exists())
            this.file.getParentFile().mkdirs();
        if (!this.file.exists()) {
            try {
                this.file.createNewFile();
            } catch (IOException e) {
                this.plugin.getLogger().log(Level.SEVERE, String.format("Could not create config file '%s'.", new Object[] { this.file.getName() }), e);
            }
            Exception exception2, exception1 = null;
        }
        try {
            this.config = provider.load(this.file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, String.format("Could not load config '%s'.", new Object[] { this.file.getName() }), e);
        }
    }

    public void save() {
        try {
            provider.save(this.config, this.file);
        } catch (IOException e) {
            this.plugin.getLogger().log(Level.SEVERE, String.format("Could not save config '%s'.", new Object[] { this.file.getName() }), e);
        }
    }

    public Configuration getConfig() {
        return this.config;
    }

    public File getFile() {
        return this.file;
    }
}
