package fr.HtSTeam.HtS.Commands;

import fr.HtSTeam.HtS.Main;
import fr.HtSTeam.HtS.GameModes.FallenKingdom.CommandsFK;
import fr.HtSTeam.HtS.GameModes.UHC.SyT.CommandSyT;
import fr.HtSTeam.HtS.Teams.TeamCommand;

public class CommandsManager {
	
	public static PlayStopCommands playStop = new PlayStopCommands();
	public static CommandSyT csyt = new CommandSyT();
	
	public static void loadCommands(Main main) {
		main.getCommand("option").setExecutor(new OptionCommand(main));
		main.getCommand("start").setExecutor(new StartCommand(main));
		main.getCommand("run").setExecutor(new StartCommand(main));
		main.getCommand("team").setExecutor(new TeamCommand());
		main.getCommand("test").setExecutor(new OptionCommand(main));	
		main.getCommand("play").setExecutor(playStop);
		main.getCommand("pause").setExecutor(playStop);
		main.getCommand("end").setExecutor(playStop);
		main.getCommand("heal").setExecutor(new UtilCommands());
		main.getCommand("feed").setExecutor(new UtilCommands());
		main.getCommand("test").setExecutor(new UtilCommands());
		main.getCommand("radar").setExecutor(csyt);
		main.getCommand("target").setExecutor(csyt);
		main.getCommand("base").setExecutor(new CommandsFK());
	}
}
