package ml.lbplugins.skills.abilitis;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import ml.lbplugins.skills.Main;

public class ListenerSkills implements Listener {

	@EventHandler
	public void onVigo(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			int porcent = Main.getInstance().getConfig().getInt("Multiplicador.Vigor");
			double damage = ((porcent / 1000) * InventorySkills.returnValue(e.getEntity().getName(), Skills.vigor));

			Player en = (Player) e.getEntity();
			en.setHealth((en.getHealth() + damage));
		}
	}

	@EventHandler
	public void onDa(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			int porcent = Main.getInstance().getConfig().getInt("Multiplicador.Resistencia");
			double damage = e.getDamage()
					- ((porcent / 1000) * InventorySkills.returnValue(e.getEntity().getName(), Skills.resistencia));
			e.setDamage(damage);
		}
	}

	@EventHandler
	public void onCriti(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			int porcent = Main.getInstance().getConfig().getInt("Multiplicador.Destreza");
			if (Math.random() < (porcent / 100)) {

				double damage = ((porcent / 1000)
						* InventorySkills.returnValue(e.getDamager().getName(), Skills.destreza));

				Player en = (Player) e.getEntity();
				en.setHealth((en.getHealth() - damage));
			}
		}
	}

	// agilidade
	@EventHandler
	public void onAgi(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			int porcent = Main.getInstance().getConfig().getInt("Multiplicador.Agilidade");
			if (Math.random() < (porcent / 100)) {
				double damage = e.getDamage()
						+ ((porcent / 1000) * InventorySkills.returnValue(e.getEntity().getName(), Skills.agilidade));
				e.setDamage(damage);
			}
		}
	}

	// streng
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) {
			int porcent = Main.getInstance().getConfig().getInt("Multiplicador.Forca");
			double damage = e.getDamage()
					+ ((porcent / 100) * InventorySkills.returnValue(e.getDamager().getName(), Skills.força));
			e.setDamage(damage);
		}
	}

}
