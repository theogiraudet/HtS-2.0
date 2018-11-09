package fr.HtSTeam.HtS.Options.Options.Statistics;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.EnumStats;
import fr.HtSTeam.HtS.Options.Options.Statistics.Structure.StatisticHandler;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class ArrowShotStatOption extends Option<Boolean> {
	
	public ArrowShotStatOption() {
		super(Material.ARROW, "Arrow Shot", "§2Activé", true, GUIRegister.stats);		
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
			EnumStats.ARROW_SHOT.setTracked(true);
			getItemStack().setLore("§2Activé");
		} else {
			EnumStats.ARROW_SHOT.setTracked(false);
			getItemStack().setLore("§4Désactivé");
		}
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}
	
	@EventHandler
	public void on(ProjectileLaunchEvent e) {
		if(EnumState.getState().equals(EnumState.RUNNING) && EnumStats.ARROW_SHOT.isTracked() && e.getEntityType() == EntityType.ARROW && e.getEntity().getShooter() instanceof Player)
			StatisticHandler.update(((Entity) e.getEntity().getShooter()).getUniqueId(), EnumStats.ARROW_SHOT);
	}
}