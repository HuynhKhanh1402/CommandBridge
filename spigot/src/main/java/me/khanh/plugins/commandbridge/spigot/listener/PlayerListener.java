package me.khanh.plugins.commandbridge.spigot.listener;

import lombok.Getter;
import me.khanh.plugins.commandbridge.spigot.CommandBridge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {
    @Getter
    private final CommandBridge plugin;

    public PlayerListener(CommandBridge plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        plugin.getLogger().info("event");
    }
}
