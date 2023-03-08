package me.khanh.plugins.commandbridge.bungee.data;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SwitchCommand {
    @Getter
    private final CommandManager manager;
    @Getter
    private final String serverFrom;
    @Getter
    private final String serverTo;
    @Nonnull
    @Getter
    private final List<String> commands;

    public SwitchCommand(CommandManager manager, String serverFrom, String serverTo, @Nullable List<String> commands){
        this.manager = manager;
        this.serverFrom = serverFrom;
        this.serverTo = serverTo;
        this.commands = commands == null ? new ArrayList<>() : commands;
    }

    public boolean match(ServerInfo from, ServerInfo to){
        if (serverFrom.equals("*") || (from != null && from.getName().equalsIgnoreCase(serverFrom))){
            return serverTo.equals("*") || to.getName().equalsIgnoreCase(serverTo);
        }
        return false;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void execute(ProxiedPlayer player){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("CommandBridge");
        output.writeUTF("Switch");
        output.writeUTF(player.getServer().getInfo().getName());
        output.writeUTF(prepareCommands(player));
        player.getServer().sendData("BungeeCord", output.toByteArray());
    }

    @SneakyThrows
    private String prepareCommands(ProxiedPlayer player){
        List<String> list = commands.stream().map(s -> s.replace("%player%", player.getName())).collect(Collectors.toList());
        return String.join("ᛙ", list);
    }
}
