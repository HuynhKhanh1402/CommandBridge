package me.khanh.plugins.commandbridge.bungee.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import me.khanh.plugins.commandbridge.bungee.CommandBridge;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PlayerListener implements Listener {
    @Getter
    private final CommandBridge plugin;

    public PlayerListener(CommandBridge plugin){
        this.plugin = plugin;
    }

//    @EventHandler
//    public void onJoin(PostLoginEvent event){
//        plugin.getCommandManager().getJoinCommands().forEach(joinCommand -> {
//            if (joinCommand.getServer().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())){
//                joinCommand.execute(event.getPlayer());
//            }
//        });
//    }

    @EventHandler
    public void onSwitch(ServerSwitchEvent event){
        plugin.getCommandManager().getJoinCommands().forEach(joinCommand -> {
            if (joinCommand.getServer().equalsIgnoreCase(event.getPlayer().getServer().getInfo().getName())){
                joinCommand.execute(event.getPlayer());
            }
        });
        plugin.getCommandManager().getSwitchCommands().forEach(switchCommand -> {
            if (switchCommand.match(event.getFrom(), event.getPlayer().getServer().getInfo())){
                switchCommand.execute(event.getPlayer());
            }
        });
    }
}
