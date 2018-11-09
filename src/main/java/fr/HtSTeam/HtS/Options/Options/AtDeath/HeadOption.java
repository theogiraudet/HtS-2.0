package fr.HtSTeam.HtS.Options.Options.AtDeath;

import org.bukkit.Material;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;

public class HeadOption extends Option<Boolean> {
	
	public HeadOption() {
		super(Material.PLAYER_HEAD, "Drop de tête", "§2Activé", true, GUIRegister.atDeath);
		getParent().update(this);
//		Main.deathLoot.addItem(Material.PLAYER_HEAD);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}

	@Override
	public void setState(Boolean value) {
		if(value && !value) {
			getItemStack().setLore("§2Activé");
//			Main.deathLoot.addItem(Material.PLAYER_HEAD);
		} else if(!value && value) {
			getItemStack().setLore("§4Désactivé");
//			Main.deathLoot.removeItem(Material.PLAYER_HEAD);
		}
		this.value = value;
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return "§2[Aide]§r À la mort du joueur, ce dernier droppera sa tête (simple objet décoratif).";
	}
}
