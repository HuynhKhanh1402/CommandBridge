package me.khanh.plugins.commandbridge.spigot.file;

import lombok.Getter;
import lombok.SneakyThrows;
import me.khanh.plugins.commandbridge.spigot.CommandBridge;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

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
    private final FileConfiguration defConfiguration;
    @Getter
    private final FileConfiguration configuration;

    @SneakyThrows
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public ConfigFile(CommandBridge plugin){
        this.plugin = plugin;

        file = new File(plugin.getDataFolder(), "config.yml");

        Reader reader = new InputStreamReader(Objects.requireNonNull(plugin.getResource("spigot-config.yml")));
        defConfiguration = YamlConfiguration.loadConfiguration(reader);
        reader.close();

        if (!file.exists()){
            file.getParentFile().mkdirs();
            Files.copy(Objects.requireNonNull(plugin.getResource("spigot-config.yml")), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        configuration = YamlConfiguration.loadConfiguration(file);

        int defVersion = defConfiguration.getInt("version");
        int version = configuration.getInt("version", 0);

        if (defVersion > version) {
            update(version);
            configuration.set("version", defVersion);
            save();
        }

    }

    public void save() throws IOException {
        configuration.save(file);
    }

    public void update(int version) {
    }

}
