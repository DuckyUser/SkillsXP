package ml.lbplugins.skills.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import ml.lbplugins.skills.Main;

public class GovenLevel implements Listener {

	/*public int total = 0, exp = 0;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();

		total = p.getLevel();
		exp = p.getTotalExperience();
		
		p.setTotalExperience(0);

		new BukkitRunnable() {

			@Override
			public void run() {
				p.setLevel(total);
				p.setTotalExperience(exp);
			}
		}.runTaskLater(Main.getInstance(), 20L*2);

	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onNaturalyXP(PlayerExpChangeEvent e) {
		e.setAmount(0);
	}*/
}
