package ml.lbplugins.skills.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ml.lbplugins.skills.abilitis.InventorySkills;


public class ExecutorCommand implements CommandExecutor {
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			return true;
		}

		if (cmd.getName().equalsIgnoreCase("skills")) {
			Player p = (Player)sender;
			InventorySkills.openMenu(p);
		}
		return false;
	}
	

}
