package ml.lbplugins.skills.points;

import java.util.HashMap;

import org.bukkit.Bukkit;

import ml.lbplugins.skills.Main;

public class ManagerPoints {

	private Main plugin;

	public ManagerPoints(Main plugin) {
		this.plugin = plugin;
	}

	public HashMap<String, Integer> points = new HashMap<>();

	public void takePoints(String nick, int amount) {

		if (points.containsKey(nick)) {
			int total = points.get(nick) - amount;
			if (total >= 1) {
				points.put(nick, total);
			} else {
				points.remove(nick);
			}
		}
	}

	public void addPoints(String nick, int amount) {

		if (points.containsKey(nick)) {
			int total = points.get(nick) + amount;
			points.put(nick, total);
		} else {
			points.put(nick, amount);
		}
	}

	public Integer getPoints(String nick) {
		if (points.containsKey(nick)) {
			return points.get(nick);
		}
		return 0;
	}

}
