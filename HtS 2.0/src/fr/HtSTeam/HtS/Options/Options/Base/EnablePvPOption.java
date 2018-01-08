package fr.HtSTeam.HtS.Options.Options.Base;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Options.Structure.Annotation.Timer;

public class EnablePvPOption extends OptionBuilder {
	

	private boolean request;
	private Player p;
	
	public EnablePvPOption() {
		super(Material.DIAMOND_SWORD, "Activation du PvP", "§220 minutes", "20", OptionRegister.base);
		switchState(false);
	}

	@Override
	public void event(Player p) {
		this.p = p;
		p.closeInventory();
		request = true;
		p.sendMessage("§2Veuillez saisir le délais d'activation du PvP.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 0 && value <= 60) {
					setValue(Integer.toString(value));
					p.sendMessage("§2PvP activé à " + getValue() + " minutes." );
					this.getItemStackManager().setLore("§2" + value + " minutes");
					parent.update(this);
					request = false;
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 0 et 60.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	
	private void switchState(boolean b) {
		for (World world : Bukkit.getWorlds())
			world.setPVP(b);
	}
	
	@Timer
	public void changeState() {
		Bukkit.broadcastMessage("§4Le PvP est maintenant activée.");
		switchState(true);
	}

}
