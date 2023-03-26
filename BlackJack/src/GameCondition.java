import java.util.List;

// GameCondition class is responsible for checking if a game move meets the requirements
public class GameCondition {
    // gameMeetsTheRequirements method checks if the current move is valid based on the game state
    public static boolean gameMeetsTheRequirements(Analyzer analyzer, int lastTimestamp, int lastPlayerID, int index, List<Analyzer> ls, int lastGameID) {
        // Check if the game has started correctly
        if (analyzer.getGameID() != lastGameID) {
            if (ErrorDetector.checkGameStart(analyzer) == -1) {
                return false;
            }
        }
        // Calculate the points for the player and the dealer
        int pointsPlayer = PointCalculator.calculatePoints(analyzer.getHandPlayer()); //Player points in this turn
        int pointsDealer = PointCalculator.calculatePoints(analyzer.getHandDealer()); //Dealer points in this turn

        // If any of the hands have invalid points, return false
        if (pointsDealer == -1 || pointsPlayer == -1) {
            return false;
        }
        // Check if the dealer and player have the same cards
        if (ErrorDetector.checkSameCards(analyzer.getHandDealer(), analyzer.getHandPlayer()) == -1) {
            return false;
        }
        // Check if the action is valid based on the current and next hands
        if (index < ls.size()) {
            if (ErrorDetector.checkAction(analyzer.getAction(), analyzer.getHandDealer(), analyzer.getHandPlayer(), ls.get(index).getHandDealer(), ls.get(index).getHandPlayer()) == -1) {
                return false;
            }
        } else {
            if (ErrorDetector.checkAction(analyzer.getAction(), analyzer.getHandDealer(), analyzer.getHandPlayer(), analyzer.getHandDealer(), analyzer.getHandPlayer()) == -1) {
                return false;
            }
        }
        // Check if the game outcome is correct
        if (analyzer.getAction().equals("D Win") || analyzer.getAction().equals("P Win") || analyzer.getAction().equals("D Lose") || analyzer.getAction().equals("P Lose")) {
            if (ErrorDetector.checkGameOutcome(analyzer, pointsPlayer, pointsDealer) == -1) {
                return false;
            }
        }
        // Check if the timestamp, player ID, and points are within valid bounds
        if (!(analyzer.getTimestamp() > lastTimestamp && analyzer.getPlayerID() == lastPlayerID && pointsPlayer <= 21 && pointsDealer <= 21)) {
            return false;
        }
        // Check if the dealer is not hitting when they should not
        if (pointsDealer >= 17 && analyzer.getAction().equals("D Hit")) {
                    return false;
        }
        return true;
    }
}
