package org.eric;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class AdminToolsListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (!(e.getWhoClicked() instanceof Player)) return;
        Player admin = (Player) e.getWhoClicked();

        if (!AdminTools.searchingTarget.containsKey(admin)) return;

        Player target = AdminTools.searchingTarget.get(admin);
        int page = AdminTools.searchingPage.get(admin);

        Inventory gui = e.getView().getTopInventory();
        int slot = e.getRawSlot();

        // ---- 1. Prevent shift-click (causes duplication/loss) ----
        if (e.isShiftClick()) {
            e.setCancelled(true);
            return;
        }

        // ---- 2. Prevent hotbar swaps (1–9 keys) ----
        if (e.getClick().isKeyboardClick()) {
            e.setCancelled(true);
            return;
        }

        // ---- 3. If click is outside GUI, ignore ----
        if (slot >= gui.getSize()) return;

        // ---- 4. Navigation arrows ----
        if (slot == 48 || slot == 50) {
            e.setCancelled(true);

            int newPage = page;
            if (slot == 48) newPage = Math.max(1, page - 1);
            if (slot == 50) newPage = Math.min(3, page + 1);

            AdminTools.searchingPage.put(admin, newPage);
            admin.openInventory(AdminToolsGUI.buildPage(admin, target, newPage));
            return;
        }

        // ---- 5. Allow editing normal slots; sync 1 tick later ----
        Bukkit.getScheduler().runTaskLater(AdminTools.getInstance(), () -> {
            sync(admin, target);
        }, 1L);
    }


    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player admin = (Player) e.getPlayer();

        if (!AdminTools.searchingTarget.containsKey(admin)) return;

        Player target = AdminTools.searchingTarget.get(admin);

        sync(admin, target);

        AdminTools.searchingTarget.remove(admin);
        AdminTools.searchingPage.remove(admin);
    }

    // -----------------------
    // Sync page → player
    // -----------------------
    private void sync(Player admin, Player target) {

        int page = AdminTools.searchingPage.get(admin);
        Inventory gui = admin.getOpenInventory().getTopInventory();

        switch (page) {

            // PAGE 1: main
            case 1: {
                ItemStack[] main = new ItemStack[36];
                for (int i = 0; i < 36; i++)
                    main[i] = gui.getItem(i);
                target.getInventory().setContents(main);
                break;
            }

            // PAGE 2: armor + offhand
            case 2: {
                target.getInventory().setHelmet(gui.getItem(20));
                target.getInventory().setChestplate(gui.getItem(29));
                target.getInventory().setLeggings(gui.getItem(38));
                target.getInventory().setBoots(gui.getItem(47));
                target.getInventory().setItemInOffHand(gui.getItem(24));
                break;
            }

            // PAGE 3: ender chest
            case 3: {
                ItemStack[] ec = gui.getContents();
                target.getEnderChest().setContents(ec);
                break;
            }
        }
    }
}
