package fr.HtSTeam.HtS.Options.Options.UHC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.HtSTeam.HtS.Options.OptionsRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionsManager;

public class BorderOption extends OptionsManager {
	
	
	private boolean request = false;
	private Player p;
	private WorldBorder border = Bukkit.getWorld("world").getWorldBorder();
	
	public BorderOption() {
		super(Material.IRON_FENCE, "Taille de la bordure", "§d1000 * 1000", "1000", OptionsRegister.uhc);
		border.setCenter(0.0, 0.0);
		border.setSize(1000);
	}

	@Override
	public void event(Player p) {
		request = true;
		p.closeInventory();
		this.p = p;
		p.sendMessage("§2Veuillez entrer la distance de la bordure par rapport au 0;0.");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {		
		if(request && e.getPlayer().equals(p)) {
			e.setCancelled(true);
			try {
				int value = Integer.parseInt(e.getMessage());
				if(value >= 500 && value <= 2500) {
					setValue(Integer.toString(value * 2));
					p.sendMessage("§2Bordure à " + value + " blocs du centre." );
					this.getItemStackManager().setLore("§d" + value * 2 + " * " + value * 2);
					parent.update(this);
					request = true;
					border.setSize(value * 2);
					return;
				}
				p.sendMessage("§4Valeur non comprise entre 500 et 2500.");
			} catch(NumberFormatException e2) {
				p.sendMessage("§4Valeur invalide.");
			}
		}
	}
	


}
