package ml.lbplugins.skills.abilitis;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import ml.lbplugins.skills.Main;

public class SystemCache implements Listener {

	public static HashMap<String, String> skillman = new HashMap<>();
	public static HashMap<String, String> cache = new HashMap<>();

	// xp-base
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!skillman.containsKey(p.getName())) {
			if (Main.getInstance().SkillsManager().contains(p.getName())) {
				skillman.put(p.getName(), Main.getInstance().SkillsManager().get(p.getName(), 1));
				Main.getInstance().ManagerPoint().points.put(p.getName(),
						Integer.valueOf(Main.getInstance().SkillsManager().get(p.getName(), 2)));
				cache.put(p.getName(), Main.getInstance().SkillsManager().get(p.getName(), 3) + "-"
						+ Main.getInstance().SkillsManager().get(p.getName(), 4));
			} else {
				String add = "forca0:agilidade0:resistencia0:vigor0:destreza0";
				skillman.put(p.getName(), add);
				Main.getInstance().ManagerPoint().points.put(p.getName(), 0);
				cache.put(p.getName(), "0-0");
			}
		}
	}

}
