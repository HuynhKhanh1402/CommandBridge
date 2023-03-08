package me.khanh.plugins.commandbridge.bungee;

import lombok.Getter;
import me.khanh.plugins.commandbridge.bungee.data.CommandManager;
import me.khanh.plugins.commandbridge.bungee.file.ConfigFile;
import me.khanh.plugins.commandbridge.bungee.listener.PlayerListener;
import net.md_5.bungee.api.plugin.Plugin;

public final class CommandBridge extends Plugin {
    @Getter
    private ConfigFile configFile;
    @Getter
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        reload();
//        getProxy().registerChannel("CommandBridge");
        getProxy().getPluginManager().registerListener(this, new PlayerListener(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void reload(){
        configFile = new ConfigFile(this);
        commandManager = new CommandManager(this);
        getLogger().info(String.format("Registered %s join command(s)", commandManager.getJoinCommands().size()));
        getLogger().info(String.format("Registered %s switch command(s)", commandManager.getSwitchCommands().size()));
    }
}
