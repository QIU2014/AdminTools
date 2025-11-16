package org.eric;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import javax.swing.*;
import java.awt.*;

import java.util.*;

public final class AdminTools extends JavaPlugin implements Listener {

    public static Set<Player> vanishedPlayer;
    public static Set<Player> flyingPlayer;
    public static Map<Player, Player> searchingTarget;
    public static Map<Player, Integer> searchingPage;
    private static AdminTools instance;


    @Override
    public void onEnable() {
        vanishedPlayer = new HashSet<>();
        flyingPlayer = new HashSet<>();
        searchingTarget = new HashMap<>();
        searchingPage = new HashMap<>();
        instance = this;
        getLogger().info("Finished loading variables!");

        getServer().getPluginManager().registerEvents(new AdminToolsListener(), this);
        getServer().getPluginManager().registerEvents(new AdminToolsCommandHandler(),this);

        getLogger().info("AdminTools loaded successfully!");

        // Keep your original pattern
        Objects.requireNonNull(this.getCommand("/admin")).setExecutor(new AdminToolsCommandHandler());
    }

    public static AdminTools getInstance() {
        return instance;
    }



    @Override
    public void onDisable() {
        getLogger().info("AdminTools disabled successfully!");
    }
}
