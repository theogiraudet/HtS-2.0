package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class DamageGivenStatOption extends Option<Boolean> {
	
	public DamageGivenStatOption() {
		super(Material.GOLDEN_SWORD, "Damage Given", "§2Activé", true, GUIRegister.stats);		
	}

	@Override
	public void event(Player p) {
		setState(!getValue());
	}

	@Override
	public void setState(Boolean value) {
		if (EnumState.getState().equals(EnumState.RUNNING))
			return;
		this.value = value;
		if(getValue()) {
			EnumStats.DAMAGE_GIVEN.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.DAMAGE_GIVEN.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(EntityDamageByEntityEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.DAMAGE_GIVEN.isTracked() && e.getDamager() instanceof Player && e.getEntity() instanceof Player)
			StatisticHandler.update(e.getDamager().getUniqueId(), EnumStats.DAMAGE_GIVEN, (int)e.getDamage());
	}
}