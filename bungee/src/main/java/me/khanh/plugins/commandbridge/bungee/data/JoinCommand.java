package me.khanh.plugins.commandbridge.bungee.data;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lombok.Getter;
import lombok.SneakyThrows;
import me.khanh.plugins.commandbridge.bungee.CommandBridge;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JoinCommand {
    @Getter
    private final CommandManager manager;
    @Getter
    private final String server;

    @Getter
    @Nonnull
    private final List<String> commands;

    public JoinCommand(CommandManager manager, @Nonnull String server, @Nullable List<String> commands){
        this.manager = manager;
        this.server = server;
        this.commands = commands == null ? new ArrayList<>() : commands;
    }

    @SuppressWarnings("UnstableApiUsage")
    public void execute(ProxiedPlayer player){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("CommandBridge");
        output.writeUTF("Join");
        output.writeUTF(server);
        output.writeUTF(prepareCommands(player));
        player.getServer().sendData("BungeeCord", output.toByteArray());
    }

    @SneakyThrows
    private String prepareCommands(ProxiedPlayer player){
        List<String> list = commands.stream().map(s -> s.replace("%player%", player.getName())).collect(Collectors.toList());
        return String.join("ᛙ", list);
    }
}
