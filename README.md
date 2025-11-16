# AdminTools  
A powerful Bukkit/Spigot/Paper admin utility plugin featuring a multi-page inventory editor, vanish mode, flight toggles, GUI player inspection, and more.

Designed for Minecraft 1.20+.

---

## ✨ Features

### ✔ Multi-Page Player Inventory Editor  
A fully interactive, editable GUI that allows administrators to inspect and modify a player’s:

- Main inventory (slots 0–35)
- Armor (helmet, chestplate, leggings, boots)
- Offhand item
- Ender chest contents

With protection against:

- Shift-click exploits  
- Hotbar (1–9) swaps  
- Dragging items out of GUI  
- Stealing the navigation arrows  
- Self-editing item loss  

Inventory edits sync **live** to the target player, even when editing yourself.

---

### ✔ Vanish Mode  
`//admin vanish`  
- Hide from all players  
- No particle emissions  
- No name tag visibility  
- Reappears cleanly when toggled off  

---

### ✔ Flight Toggle  
`//admin fly`  
- Enables/disables flying mode  
- Works in survival mode  

---

### ✔ GUI Landing Window (Optional)  
If the JAR is run outside a Minecraft server, a Swing GUI appears explaining how to install and use the plugin.  
(Implemented via `GUILanding.java`.)

---

## 🧭 Multi-Page Inventory Editor

AdminTools includes a professional multi-page GUI system:

| Page | Description |
|------|-------------|
| **Page 1** | Main Inventory (36 slots) |
| **Page 2** | Armor + Offhand |
| **Page 3** | Ender Chest |

With **Next Page** and **Previous Page** buttons at slot **48** and **50**.

---

## 🔧 Commands

### `/admin search <player>`
Open the target player’s multi-page editable GUI.

### `/admin vanish`
Toggle vanish mode.

### `/admin fly`
Toggle flying mode.

---

## 🔐 Permissions

| Permission | Description |
|-----------|-------------|
| `admintools.search` | Allows `/admin search` |
| `admintools.vanish` | Allows `/admin vanish` |
| `admintools.fly` | Allows `/admin fly` |
| `admintools.*` | Grants all permissions |

(If permissions are not needed, this section can be ignored.)

---

## 📦 Installation

1. Download the plugin JAR from the **Releases** page.  
2. Place it in your server’s `plugins/` directory.  
3. Restart (or reload) the server.  
4. Use the commands listed above.

---

## 📘 Build Instructions (Maven)

```bash
mvn clean package
```

## 🐞 Known Bug

### Players can steal the Arrow from the GUI on page 2

## 📄 Reporting a bug

If you encounter a bug please report it <a href="https://github.com/QIU2014/AdminTools/issues">here</a>
