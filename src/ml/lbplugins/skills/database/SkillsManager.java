package ml.lbplugins.skills.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ml.lbplugins.skills.Main;

public class SkillsManager {

	private Main plugin;

	public SkillsManager(Main plugin) {
		this.plugin = plugin;
	}

	public void update(String player, String inset, int pontos, double xp, double base) {
		PreparedStatement stm = null;
		try {
			stm = Main.con.prepareStatement("UPDATE " + Main.tabela + " SET inset ='" + inset + "', pontos ='" + pontos
					+ "', xp ='" + xp + "', base ='" + base + "'  WHERE nick='" + player.toLowerCase() + "'");
			stm.execute();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	public void setPlayer(String nome, String inset, int pontos, double xp, double base) {
		try {
			PreparedStatement ps = Main.con
					.prepareStatement("INSERT INTO `" + Main.tabela + "` (`nick`, `inset`, `pontos`, `xp`, `base`) VALUES (?,?,?,?,?)");
			ps.setString(1, nome.toLowerCase());
			ps.setString(2, inset);
			ps.setInt(3, pontos);
			ps.setDouble(4, xp);
			ps.setDouble(5, base);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean contains(String player) {
		try (PreparedStatement ps = Main.con
				.prepareStatement("SELECT * FROM " + Main.tabela + " WHERE nick='" + player.toLowerCase() + "'")) {
			ResultSet rs = ps.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String get(String player, int value) {
		PreparedStatement stm = null;
		try {
			stm = Main.con.prepareStatement("SELECT * FROM " + Main.tabela + " WHERE `nick` = ?");
			stm.setString(1, player.toLowerCase());
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				if (value == 1) {
					return rs.getString("inset");
				} else if (value == 2) {
					return rs.getString("pontos");
				} else if (value == 3) {
					return rs.getString("xp");
				} else {
					return rs.getString("base");
				}
			}
			return null;
		} catch (SQLException e) {
			return null;
		}
	}

}
