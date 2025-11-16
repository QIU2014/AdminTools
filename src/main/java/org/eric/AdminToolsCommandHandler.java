package org.eric;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdminToolsCommandHandler implements CommandExecutor, Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player admin = (Player) e.getWhoClicked();

        // not one of our GUIs
        if (!AdminTools.searchingTarget.containsKey(admin)) return;

        Player target = AdminTools.searchingTarget.get(admin);

        Inventory gui = e.getInventory();

        // allow editing normally, but update target after the click
        Bukkit.getScheduler().runTaskLater(AdminTools.getInstance(), () -> {
            ItemStack[] updated = gui.getContents();
            target.getInventory().setContents(updated);
        }, 1L);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player admin = (Player) e.getPlayer();

        if (!AdminTools.searchingTarget.containsKey(admin)) return;

        Player target = AdminTools.searchingTarget.get(admin);
        Inventory gui = e.getInventory();

        // final sync when admin closes the window
        target.getInventory().setContents(gui.getContents());

        AdminTools.searchingTarget.remove(admin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("§4Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§2Usage: //admin <subcommand>");
            return true;
        }

        String sub = args[0].toLowerCase();

        switch (sub) {

            // -----------------------------------------------------
            // VANISH
            // -----------------------------------------------------
            case "vanish":
                if (AdminTools.vanishedPlayer.contains(player)) {
                    AdminTools.vanishedPlayer.remove(player);

                    // make player visible to everyone
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.showPlayer(player);
                    }

                    player.sendMessage("§aYou are now visible.");
                } else {
                    AdminTools.vanishedPlayer.add(player);

                    // hide player from everyone
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p != player) p.hidePlayer(player);
                    }

                    player.sendMessage("§aYou are now vanished.");
                }
                return true;

            // -----------------------------------------------------
            // FLY
            // -----------------------------------------------------
            case "fly":
                if (AdminTools.flyingPlayer.contains(player)) {
                    AdminTools.flyingPlayer.remove(player);
                    player.setAllowFlight(false);
                    player.setFlying(false);
                    player.sendMessage("§aYou are no longer flying.");
                } else {
                    AdminTools.flyingPlayer.add(player);
                    player.setAllowFlight(true);
                    player.setFlying(true);
                    player.sendMessage("§aFly mode enabled.");
                }
                return true;

            // -----------------------------------------------------
            // SEARCH
            // Opens a large chest GUI showing another player's inventory
            // -----------------------------------------------------
            case "search":
                if (args.length < 2) {
                    player.sendMessage("§cUsage: //admin search <player>");
                    return true;
                }

                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    player.sendMessage("§cPlayer not found!");
                    return true;
                }

                // Page 1
                AdminTools.searchingTarget.put(player, target);
                AdminTools.searchingPage.put(player, 1);

                player.openInventory(AdminToolsGUI.buildPage(player, target, 1));
                return true;





            // -----------------------------------------------------
            // HELP
            // -----------------------------------------------------
            case "help":
                sender.sendMessage("§3+---------AT HELP---------+");
                sender.sendMessage("§3| //admin fly              ");
                sender.sendMessage("§3| //admin vanish           ");
                sender.sendMessage("§3| //admin search <player>  ");
                sender.sendMessage("§3+-------------------------+");
                return true;

            // -----------------------------------------------------
            // UNKNOWN COMMAND
            // -----------------------------------------------------
            default:
                sender.sendMessage("§4Unknown command! Use //admin help.");
                return true;
        }
    }
}
