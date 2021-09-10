package ml.lbplugins.skills;

import java.io.File;
import java.sql.Connection;

import org.bukkit.plugin.java.JavaPlugin;

import ml.lbplugins.skills.abilitis.InventorySkills;
import ml.lbplugins.skills.abilitis.ListenerSkills;
import ml.lbplugins.skills.abilitis.SystemCache;
import ml.lbplugins.skills.commands.ExecutorCommand;
import ml.lbplugins.skills.database.MySQL;
import ml.lbplugins.skills.database.SkillsManager;
import ml.lbplugins.skills.events.GovenLevel;
import ml.lbplugins.skills.events.GovenXP;
import ml.lbplugins.skills.points.ManagerPoints;

public class Main extends JavaPlugin {

	public static Connection con;
	public static String tabela = "skills";

	@Override
	public void onEnable() {
		if (!(new File(this.getDataFolder(), "config.yml")).exists()) {
			this.saveDefaultConfig();
		}
		register();
		MySQL.openConnection();
	}

	@Override
	public void onDisable() {
		for (String reup : SystemCache.skillman.keySet()) {
			String[] system = SystemCache.cache.get(reup).split("-");
			if (SkillsManager().contains(reup)) {
				SkillsManager().update(reup, SystemCache.skillman.get(reup), ManagerPoint().getPoints(reup),
						Double.valueOf(system[0]), Double.valueOf(system[1]));
			} else {
				SkillsManager().setPlayer(reup, SystemCache.skillman.get(reup), ManagerPoint().getPoints(reup),
						Double.valueOf(system[0]), Double.valueOf(system[1]));
			}
		}
	}

	ManagerPoints mn = new ManagerPoints(this);
	SkillsManager sk = new SkillsManager(this);

	public ManagerPoints ManagerPoint() {
		return mn;
	}

	public SkillsManager SkillsManager() {
		return sk;
	}

	public static Main getInstance() {
		return getPlugin(Main.class);
	}

	public void register() {
		getServer().getPluginManager().registerEvents(new GovenXP(), this);
		getServer().getPluginManager().registerEvents(new GovenLevel(), this);
		getServer().getPluginManager().registerEvents(new InventorySkills(), this);
		getServer().getPluginManager().registerEvents(new ListenerSkills(), this);
		getServer().getPluginManager().registerEvents(new SystemCache(), this);
		getCommand("skills").setExecutor(new ExecutorCommand());
	}
}
