package fr.HtSTeam.HtS.GameModes.UHC.SyT;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.HtSTeam.HtS.Main;

public class TakeDamageEvent implements Listener {
	
	@EventHandler(priority = EventPriority.LOWEST)
	public void onTakeDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(((Player) e.getEntity()).getHealth() <= 10f && Main.timer.getTimerInMinute() < 20) {
				e.setCancelled(true);
				((Player) e.getEntity()).setHealth(10f);
			}
		}
	}

}
