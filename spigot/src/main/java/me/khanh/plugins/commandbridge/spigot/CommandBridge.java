package me.khanh.plugins.commandbridge.spigot;

import lombok.Getter;
import me.khanh.plugins.commandbridge.spigot.file.ConfigFile;
import me.khanh.plugins.commandbridge.spigot.listener.MessageListener;
import me.khanh.plugins.commandbridge.spigot.listener.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandBridge extends JavaPlugin {
    @Getter
    private ConfigFile configFile;
    @Getter
    private String serverName;
    @Getter
    private MessageListener messageListener;

    @Override
    public void onEnable() {
        configFile = new ConfigFile(this);
        serverName = configFile.getConfiguration().getString("server-name");

        messageListener = new MessageListener(this);
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", messageListener);
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        getServer().getMessenger().unregisterIncomingPluginChannel(this, "BungeeCord", messageListener);
        getServer().getMessenger().unregisterOutgoingPluginChannel(this);
    }
}
