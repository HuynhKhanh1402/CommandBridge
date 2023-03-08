package me.khanh.plugins.commandbridge.bungee.file;

import lombok.Getter;
import lombok.SneakyThrows;
import me.khanh.plugins.commandbridge.bungee.CommandBridge;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class ConfigFile {
    @Getter
    private final CommandBridge plugin;
    @Getter
    private final File file;
    @Getter
    private final Configuration defConfiguration;
    @Getter
    private final Configuration configuration;

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ConfigFile(CommandBridge plugin){
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "config.yml");

        Reader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResourceAsStream("bungee-config.yml")));
        defConfiguration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(reader);
        reader.close();


        if (!file.exists()){
            file.getParentFile().mkdirs();
            Files.copy(Objects.requireNonNull(plugin.getResourceAsStream("bungee-config.yml")), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

        int defVersion = defConfiguration.getInt("version");
        int version = configuration.getInt("version", 0);

        if (defVersion > version) {
            update(version);
            configuration.set("version", defVersion);
            save();
        }
    }

    public void save() throws IOException {
        YamlConfiguration.getProvider(YamlConfiguration.class).save(configuration, file);
    }

    public void update(int version) {
    }
}
