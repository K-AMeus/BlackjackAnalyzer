
// Analyzer class represents a single move in the game
public class Analyzer{
    final int timestamp;
    final int gameID;
    final int playerID;
    final String action;
    final String handDealer;
    final String handPlayer;

    // Constructor for the Analyzer class
    public Analyzer(int timestamp, int gameID, int playerID, String action, String handDealer, String handPlayer) {
        this.timestamp = timestamp;
        this.gameID = gameID;
        this.playerID = playerID;
        this.action = action;
        this.handDealer = handDealer;
        this.handPlayer = handPlayer;
    }

    //Getter methods
    public int getTimestamp() {
        return timestamp;
    }

    public int getGameID() {
        return gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public String getAction() {
        return action;
    }

    public String getHandDealer() {
        return handDealer;
    }

    public String getHandPlayer() {
        return handPlayer;
    }

    // Returns a string representation of the Analyzer object
    @Override
    public String toString() {
        return "Analyzer{" +
                "timestamp='" + timestamp + '\'' +
                ", gameID=" + gameID +
                ", playerID=" + playerID +
                ", action='" + action + '\'' +
                ", handDealer='" + handDealer + '\'' +
                ", handPlayer='" + handPlayer + '\'' +
                '}';
    }
}
