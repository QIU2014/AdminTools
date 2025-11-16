package org.eric;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AdminToolsGUI {

    public static Inventory buildPage(Player admin, Player target, int page) {
        switch (page) {
            case 1: return buildMainPage(target);
            case 2: return buildArmorPage(target);
            case 3: return buildEnderPage(target);
            default: return buildMainPage(target);
        }
    }

    // ----------------------------
    // PAGE 1: Main inventory
    // ----------------------------
    private static Inventory buildMainPage(Player target) {
        Inventory gui = Bukkit.createInventory(null, 54,
                "§8Inv: " + target.getName() + " §7(Page 1)");

        ItemStack[] contents = target.getInventory().getContents();
        for (int i = 0; i < 36; i++)
            gui.setItem(i, contents[i]);

        addNavButtons(gui);
        return gui;
    }

    // ----------------------------
    // PAGE 2: Armor + Offhand
    // ----------------------------
    private static Inventory buildArmorPage(Player target) {
        Inventory gui = Bukkit.createInventory(null, 54,
                "§8Inv: " + target.getName() + " §7(Page 2)");

        gui.setItem(20, target.getInventory().getHelmet());
        gui.setItem(29, target.getInventory().getChestplate());
        gui.setItem(38, target.getInventory().getLeggings());
        gui.setItem(47, target.getInventory().getBoots());
        gui.setItem(24, target.getInventory().getItemInOffHand());

        addNavButtons(gui);
        return gui;
    }

    // ----------------------------
    // PAGE 3: Ender chest
    // ----------------------------
    private static Inventory buildEnderPage(Player target) {
        Inventory gui = Bukkit.createInventory(null, 54,
                "§8Ender: " + target.getName() + " §7(Page 3)");

        ItemStack[] contents = target.getEnderChest().getContents();
        for (int i = 0; i < contents.length && i < 54; i++)
            gui.setItem(i, contents[i]);

        addNavButtons(gui);
        return gui;
    }

    // ----------------------------
    // Navigation buttons
    // ----------------------------
    private static void addNavButtons(Inventory gui) {
        gui.setItem(48, navItem(Material.ARROW, "§aPrevious Page"));
        gui.setItem(50, navItem(Material.ARROW, "§aNext Page"));
    }

    private static ItemStack navItem(Material m, String name) {
        ItemStack i = new ItemStack(m);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(name);
        i.setItemMeta(meta);
        return i;
    }
}
