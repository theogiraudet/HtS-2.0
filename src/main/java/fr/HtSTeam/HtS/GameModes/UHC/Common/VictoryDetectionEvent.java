package fr.HtSTeam.HtS.GameModes.UHC.Common;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.JSON;

public class VictoryDetectionEvent implements Listener {
	
	private final boolean teamVictoryDetection;
	

	public VictoryDetectionEvent(boolean b) {
		teamVictoryDetection = b;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(teamVictoryDetection && TeamBuilder.teamList.size() == 1) {
			EnumState.setState(EnumState.FINISHING);
			JSON.sendAll(TeamBuilder.teamList.get(0).getTeamColor() + "La team" + TeamBuilder.teamList.get(0).getTeamName() + " a gagné !", null, 5);
		} else if(PlayerInGame.playerInGame.size() == 1) {
			EnumState.setState(EnumState.FINISHING);
			JSON.sendAll(Bukkit.getPlayer(PlayerInGame.playerInGame.get(0)).getName() + " §2 a gagné !", null, 5);
		}
	}
	
}
