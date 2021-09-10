package ml.lbplugins.skills.database;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;

import ml.lbplugins.skills.Main;


public class MySQL {

	public static String tabela;
	
	public static void openConnection() {

		if (Main.getInstance().getConfig().getBoolean("MySQL.Enable") == true) {
			String url = "jdbc:mysql://" + Main.getInstance().getConfig().getString("MySQL.Host") + ":"
					+ Main.getInstance().getConfig().getString("MySQL.Porta") + "/"
					+ Main.getInstance().getConfig().getString("MySQL.Database");
			String user = Main.getInstance().getConfig().getString("MySQL.User");
			String password = Main.getInstance().getConfig().getString("MySQL.Password");

			try {
				Main.con = DriverManager.getConnection(url, user, password);
			} catch (SQLException e) {
				Bukkit.getServer().getPluginManager().disablePlugin(Main.getInstance());
			}
			tabela = "skills";
			PreparedStatement stm = null;

			try {
				stm = Main.con
						.prepareStatement("CREATE TABLE IF NOT EXISTS `skills`(`nick` VARCHAR(24), `inset` TEXT, `pontos` int, `xp` DOUBLE, `base` DOUBLE)");
				stm.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else {
			File bansdb = new File(Main.getInstance().getDataFolder(), "skills.db");
			if (!bansdb.exists()) { 
				try {
					bansdb.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				Class.forName("org.sqlite.JDBC");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			try {
				Main.con = DriverManager.getConnection("jdbc:sqlite:" + Main.getInstance().getDataFolder() + File.separator + "skills.db");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				PreparedStatement ps = Main.con.prepareStatement("CREATE TABLE IF NOT EXISTS skills(nick varchar(24) NOT NULL, inset STRING, pontos INTEGER, xp DOUBLE, base DOUBLE)");
				ps.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}