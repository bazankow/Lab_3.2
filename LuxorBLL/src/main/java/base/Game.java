package base;

import java.util.ArrayList;
import java.util.UUID;

public class Game {

	private UUID GameID;
	private ArrayList<UUID> TableIDs = new ArrayList<UUID>();
	private ArrayList<Player> GamePlayers = new ArrayList<Player>();
	
	public Game(UUID gameID, ArrayList<UUID> tableIDs, ArrayList<Player> gamePlayers) {
		super();
		GameID = gameID;
		TableIDs = tableIDs;
		GamePlayers = gamePlayers;
	}

	public UUID getGameID() {
		return GameID;
	}

	public void setGameID(UUID gameID) {
		GameID = gameID;
	}

	public ArrayList<UUID> getTableIDs() {
		return TableIDs;
	}

	public void setTableIDs(ArrayList<UUID> tableIDs) {
		TableIDs = tableIDs;
	}

	public ArrayList<Player> getGamePlayers() {
		return GamePlayers;
	}

	public void setGamePlayers(ArrayList<Player> gamePlayers) {
		GamePlayers = gamePlayers;
	}
	
	
	public void AddPlayerToGame(Table t, Player p){
		t.AddPlayerToTable(p);
	}
}
