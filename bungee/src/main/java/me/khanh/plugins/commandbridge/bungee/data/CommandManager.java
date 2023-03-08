package me.khanh.plugins.commandbridge.bungee.data;

import lombok.Getter;
import me.khanh.plugins.commandbridge.bungee.CommandBridge;
import net.md_5.bungee.config.Configuration;

import java.util.HashSet;
import java.util.Set;

public class CommandManager {
    @Getter
    private final CommandBridge plugin;
    @Getter
    private final Set<JoinCommand> joinCommands = new HashSet<>();
    @Getter
    private final Set<SwitchCommand> switchCommands = new HashSet<>();

    public CommandManager(CommandBridge plugin){
        this.plugin = plugin;

        Configuration configuration = plugin.getConfigFile().getConfiguration();

        if (configuration.getSection("onJoin") != null){
            for (String key: configuration.getSection("onJoin").getKeys()){
                try {
                    joinCommands.add(new JoinCommand(this, key, configuration.getStringList("onJoin." + key)));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        if (configuration.getSection("onSwitch") != null){
            for (String key: configuration.getSection("onSwitch").getKeys()){
                try {
                    String from = configuration.getString("onSwitch." + key + ".from");
                    String to = configuration.getString("onSwitch." + key + ".to");
                    switchCommands.add(new SwitchCommand(this, from, to, configuration.getStringList("onSwitch." + key + ".commands")));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
