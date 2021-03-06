package fr.HtSTeam.HtS.GameModes.UHC.Common;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.HtSTeam.HtS.EnumState;
import fr.HtSTeam.HtS.Players.PlayerInGame;
import fr.HtSTeam.HtS.Players.PlayerRemove;
import fr.HtSTeam.HtS.Teams.TeamBuilder;
import fr.HtSTeam.HtS.Utils.JSON;

public class VictoryDetectionEvent implements Listener, PlayerRemove {

	private boolean teamVictoryDetection;

	public VictoryDetectionEvent(boolean b) {
		teamVictoryDetection = b;
		hasLast();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent e) {
		removePlayer(null, null);
	}

	@Override
	public void removePlayer(UUID uuid, String name) {
		if (EnumState.getState() != EnumState.FINISHING && teamVictoryDetection && TeamBuilder.teamList.size() == 1) {
			JSON.sendAll(ChatColor.valueOf(TeamBuilder.teamList.get(0).getTeamColor().toUpperCase()) + "La team "
					+ TeamBuilder.teamList.get(0).getTeamName() + " a gagné !", null, 5);
			EnumState.setState(EnumState.FINISHING);
		} else if (EnumState.getState() != EnumState.FINISHING && PlayerInGame.playerInGame.size() == 1) {
			JSON.sendAll(PlayerInGame.uuidToName.get(PlayerInGame.playerInGame.get(0))+ "§2 a gagné !", null, 5);
			EnumState.setState(EnumState.FINISHING);
		}
	}
}