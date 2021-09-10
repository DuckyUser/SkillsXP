package ml.lbplugins.skills.abilitis;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import ml.lbplugins.skills.Main;

public class InventorySkills implements Listener {
	
	public Integer returnPosition(String s) {
		switch (s) {
		case "forca":
			return 0;
		case "agilidade":
			return 1;
		case "resistencia":
			return 2;
		case "vigor":
			return 3;
		case "destreza":
			return 4;
		}
		return null;
	}
	
	public static Integer returnValue(String nick, Skills s) {
		String[] inset = SystemCache.skillman.get(nick).split(":");
		
		switch (s) {
		case força:
			return Integer.valueOf(inset[0].replace("forca", ""));
		case agilidade:
			return Integer.valueOf(inset[1].replace("agilidade", ""));
		case resistencia:
			return Integer.valueOf(inset[2].replace("resistencia", ""));
		case vigor:
			return Integer.valueOf(inset[3].replace("vigor", ""));
		case destreza:
			return Integer.valueOf(inset[4].replace("destreza", ""));
		}
		return null;
	}
	
	@EventHandler
	public void onSelect(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		if (e.getInventory().getTitle().equalsIgnoreCase("§8Skills")) {
			e.setCancelled(true);
			for (String mode : Main.getInstance().getConfig().getConfigurationSection("Inventario.").getKeys(false)) {
					
				if (e.getSlot() == Main.getInstance().getConfig().getInt("Inventario." + mode + ".Slot")) {
					
					String value = mode;
					if (value == "profile") {
						return;
					}
					
					if (Main.getInstance().ManagerPoint().getPoints(p.getName()) >=1) {
						Main.getInstance().ManagerPoint().takePoints(p.getName(), 1);
						p.sendMessage(Main.getInstance().getConfig().getString("Mensagens.Compra_Efetuada").replace("&", "§"));
						
						
						String[] manu = SystemCache.skillman.get(p.getName()).split(":");
						int nivel = Integer.valueOf(manu[returnPosition(value)].replace(value, ""));
						String old = manu[returnPosition(value)]; //->>forca2
						String news = value+""+(nivel+1);
						SystemCache.skillman.put(p.getName(), SystemCache.skillman.get(p.getName()).replace(old, news));
					} else {
						p.sendMessage(Main.getInstance().getConfig().getString("Mensagens.Compra_Erro").replace("&", "§"));
					}
					p.closeInventory();
				}
			}
		}
	}
	
	public static void openMenu(Player p) {
		
		Inventory inv = Bukkit.createInventory(null, 9*3, "§8Skills");
		
		for (String mode : Main.getInstance().getConfig().getConfigurationSection("Inventario").getKeys(false)) {
			
			int id = Main.getInstance().getConfig().getInt("Inventario." + mode + ".ID");
			int data = Main.getInstance().getConfig().getInt("Inventario." + mode + ".Data");

			ItemStack item = new ItemStack(Material.getMaterial(id), 1, (short)data);
			ItemMeta itemm = item.getItemMeta();
			
			itemm.setDisplayName(Main.getInstance().getConfig().getString("Inventario." + mode + ".Nome").replace("&", "§"));

			ArrayList<String> lore = new ArrayList<>();
			for (String lor : Main.getInstance().getConfig().getStringList("Inventario." + mode + ".Lore")) {
				lore.add(lor.replace("&", "§").replace("%pontos%", String.valueOf(Main.getInstance().ManagerPoint().getPoints(p.getName()))).replace("%vigor%", String.valueOf(returnValue(p.getName(), Skills.vigor))).replace("%resistencia%", String.valueOf(returnValue(p.getName(), Skills.resistencia))).replace("%destreza%", String.valueOf(returnValue(p.getName(), Skills.destreza))).replace("%forca%", String.valueOf(returnValue(p.getName(), Skills.força))).replace("%agilidade%", String.valueOf(returnValue(p.getName(), Skills.agilidade))));
			}
			itemm.setLore(lore);
			item.setItemMeta(itemm);
			inv.setItem(Main.getInstance().getConfig().getInt("Inventario." + mode + ".Slot"), item);
		}
		for (int x = 0; x < 27; x++) {
			if (inv.getItem(x) == null) {
				ItemStack tt = new ItemStack(Material.STAINED_GLASS_PANE);
				ItemMeta ttm = tt.getItemMeta();
				ttm.setDisplayName("§f");
				tt.setItemMeta(ttm);
				inv.setItem(x, tt);
			}
		}
		p.openInventory(inv);
	}

}
