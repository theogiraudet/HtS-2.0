package fr.HtSTeam.HtS.Options.Options.Others;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.OptionBuilder;
import fr.HtSTeam.HtS.Utils.Randomizer;

public class NuggetsOption extends OptionBuilder<Boolean>{

	public NuggetsOption() {
		super(Material.GOLD_NUGGET, "Loot des pépites", "§2Activé", true, GUIRegister.other);
	}

	@Override
	public void event(Player p) {
		setState(getValue());
	}
	
	
	@Override
	public void setState(Boolean value) {
		if (value)
			getItemStack().setLore("§2Activé");
		else
			getItemStack().setLore("§4Désactivé");
		setValue(value);
		parent.update(this);
	}
	

	@EventHandler
	public void onDropNuggets(PlayerBucketFillEvent e) {
		if (getValue()) {
			Player p = e.getPlayer();
			Block b = e.getBlockClicked();
			if (b.getType() == Material.STATIONARY_WATER) {
				if (Randomizer.RandRate(4))
					p.getInventory().addItem(new ItemStack(Material.GOLD_NUGGET, 1));
				else if (Randomizer.RandRate(6))
					p.getInventory().addItem(new ItemStack(Material.IRON_NUGGET, 1));
			}
		}
	}

	@Override
	public String description() {
		return "§2[Aide]§r Lors du remplissage d'un sceau d'eau, il y a 2% de chances que celui-ci loot une nugget d'or ou de fer.";
	}
}
