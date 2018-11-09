package fr.HtSTeam.HtS.Options.Options.LootTables;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.bukkit.entity.EntityType;

import fr.HtSTeam.HtS.Options.GUIRegister;
import fr.HtSTeam.HtS.Options.Structure.Option;
import fr.HtSTeam.HtS.Player.Player;
import fr.HtSTeam.HtS.Utils.ItemStackBuilder;
import fr.HtSTeam.HtS.Utils.Files.FileExtractor;

public class GhastOption extends Option<Boolean>{

	public GhastOption() {
		super(new ItemStackBuilder(EntityType.GHAST, 1, "§rGhast", "§4Désactivé"), false, GUIRegister.loottables);
	}

	@Override
	public void event(Player p) {
		setState(!value);
	}

	@Override
	public void setState(Boolean value) {
		if(value && !this.value) {
			try {
				FileExtractor.extractFile(FileExtractor.lt + "ghast.json", FileExtractor.wdir + FileExtractor.Edir);
				getItemStack().setLore("§2Activé");
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		} else if(!value && this.value){
			getItemStack().setLore("§4Désactivé");
			try {
				Files.delete(Paths.get(FileExtractor.wdir + FileExtractor.Edir + "ghast.json"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.value = value;
		getParent().update(this);
	}

	@Override
	public String getDescription() {
		return null;
	}	
}