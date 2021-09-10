package ml.lbplugins.skills.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import ml.lbplugins.skills.Main;
import ml.lbplugins.skills.abilitis.SystemCache;

public class GovenXP implements Listener {


	public static Integer qnt(Player p) {
		int x = 0;
		for (String perm : Main.getInstance().getConfig().getConfigurationSection("Permissoes").getKeys(false)) {
			if (p.hasPermission("boostxp." + perm)) {
				x = Main.getInstance().getConfig().getInt("Permissoes." + perm);
			}
		}
		return x;
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		int id = e.getBlock().getTypeId();
		int data = e.getBlock().getData();
		for (String blocks : Main.getInstance().getConfig().getConfigurationSection("Blocos").getKeys(false)) {

			String type = id + "-" + data;

			if (blocks.equalsIgnoreCase(type)) {

				int xpgiven = Main.getInstance().getConfig().getInt("Blocos." + type);
				Player p = e.getPlayer();
				if (qnt(p) > 0) {
					xpgiven += (xpgiven*(qnt(p)/100));
				}
				systemOP(p, xpgiven);
				return;
			}
		}
	}

	public void systemOP(Player p, double xpgiven) {
		String[] system = SystemCache.cache.get(p.getName()).split("-");
		for (String value : Main.getInstance().getConfig().getConfigurationSection("XP").getKeys(false)) {
			
			if (system[0].equalsIgnoreCase(value)) {
					
				Double base = Main.getInstance().getConfig().getDouble("XP."+value);
				if (Double.valueOf(system[1]) >= base) {
					int nivel = (Integer.valueOf(system[0]) + 1);
					SystemCache.cache.put(p.getName(), nivel + "-" + (0 + xpgiven + (Double.valueOf(system[1])-base)));
					Main.getInstance().ManagerPoint().addPoints(p.getName(), 1);
					p.sendMessage(Main.getInstance().getConfig().getString("Mensagens.UpLevel").replace("&", "§").replace("%nivel%", String.valueOf(nivel)));
				} else {
					SystemCache.cache.put(p.getName(), system[0] + "-" + (Double.valueOf(system[1]) + xpgiven));
				}
				//Bukkit.broadcastMessage("XP: " + system[0] + " Base: " + system[1]);
				return;
			}
		}
	}
	@EventHandler
	public void onKill(EntityDeathEvent e) {
		if (e.getEntity().getKiller() instanceof Player) {

			Entity en = e.getEntity();

			for (String mobs : Main.getInstance().getConfig().getConfigurationSection("Mobs").getKeys(false)) {

				if (en.getName().equalsIgnoreCase(mobs)) {

					double xpgiven = Main.getInstance().getConfig().getDouble("Mobs." + mobs);

					Player p = (Player) e.getEntity().getKiller();
					if (qnt(p) > 0) {
						xpgiven += (xpgiven*(qnt(p)/100));
					}
					systemOP(p, xpgiven);
					return;
				}

			}

		}
	}

}
