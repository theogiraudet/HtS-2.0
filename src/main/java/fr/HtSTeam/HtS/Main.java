package fr.HtSTeam.HtS;

import java.util.HashMap;
import java.util.UUID;

import fr.HtSTeam.HtS.Events.EventManager;
import fr.HtSTeam.HtS.GameModes.GameMode;
import fr.HtSTeam.HtS.Scoreboard.Scoreboard.ScoreboardLib;
import fr.HtSTeam.HtS.Utils.FileExtractor;
import fr.HtSTeam.HtS.Utils.TimerTask;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.HtSTeam.HtS.Commands.CommandsManager;
import fr.HtSTeam.HtS.GameModes.UHC.Common.UHC;
import fr.HtSTeam.HtS.Options.OptionRegister;
import fr.HtSTeam.HtS.Players.DeathLoot;

public class Main extends JavaPlugin {
	
	public HashMap<Player, UUID> uuidPlayer = new HashMap<>();
	public static World world;
	public static Main plugin;
	public final static String HTSNAME = "HtS I";
	public static DeathLoot deathLoot = new DeathLoot();
	public static TimerTask timer;
	public static GameMode gamemode = new UHC();
		
	@Override
	public void onEnable() {
		plugin = this;
		System.out.println("Lancement de HtS...");
		
		for(World world : Bukkit.getWorlds()) {
			world.setDifficulty(Difficulty.HARD);
			if(world.getEnvironment() == Environment.NORMAL) {
				Main.world = world; 
				FileExtractor.wdir = world.getName() + "/data/loot_tables";
				world.setPVP(false);
				world.setSpawnLocation(0, 205, 0);
			}	
		}		
		timer = new TimerTask(0, 1);
		
		Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "gamerule sendCommandFeedback false");
		EventManager.loadEvents(this);
		CommandsManager.loadCommands(this);
		OptionRegister.register();
		ScoreboardLib.setPluginInstance(this);
	}
}