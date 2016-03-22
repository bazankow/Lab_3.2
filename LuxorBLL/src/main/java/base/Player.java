package base;

import java.util.UUID;

public class Player {
	private UUID playerID;
	private String playerName;
	private int playerPosition;
	
	public Player(UUID playerID, String playerName, int playerPosition) {
		super();
		this.playerID = playerID;
		this.playerName = playerName;
		this.playerPosition = playerPosition;
	}

	public UUID getPlayerID() {
		return playerID;
	}

	public void setPlayerID(UUID playerID) {
		this.playerID = playerID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getPlayerPosition() {
		return playerPosition;
	}

	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
	}
}
