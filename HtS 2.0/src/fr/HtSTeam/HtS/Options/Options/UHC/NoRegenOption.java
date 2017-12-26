package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class NoRegenOption extends OptionsManager {
	
	private boolean activate = false;

	public NoRegenOption() {
		super(Material.RED_ROSE, "Régération naturelle", "§4Désactivé", "Désactivé", OptionsRegister.uhc);
		
	}

	@Override
	public void event(Player p) {
		activate = !activate;
		if(activate) {
			setValue("Activé");
			getItemStackManager().setLore("§2Activé");
		} else {
			setValue("Désactivé");
			getItemStackManager().setLore("§4Désactivé");
		}
		parent.update(this);
		
	}
	
	@EventHandler
	public void onFoodHealEvent(EntityRegainHealthEvent e) {
		if(!activate) {
			Entity p = e.getEntity();
			if(p instanceof Player && e.getRegainReason() == RegainReason.SATIATED)
				e.setCancelled(true);
		}
	}
}
