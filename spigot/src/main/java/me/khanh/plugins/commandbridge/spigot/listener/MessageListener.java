package me.khanh.plugins.commandbridge.spigot.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.SneakyThrows;
import me.khanh.plugins.commandbridge.spigot.CommandBridge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;


public class MessageListener implements PluginMessageListener {
    @Getter
    private final CommandBridge plugin;

    public MessageListener(CommandBridge plugin){
        this.plugin = plugin;
    }
    @SneakyThrows
    @SuppressWarnings({"NullableProblems", "UnstableApiUsage"})
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        if (!subChannel.equals("CommandBridge")){
            return;
        }

        String type = in.readUTF();


        if ((type.equals("Join") || type.equals("Switch")) && in.readUTF().equalsIgnoreCase(plugin.getServerName())) {
            String[] commands = in.readUTF().split("ᛙ");
            for (String command: commands){
                Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
            }
        }
    }
}
