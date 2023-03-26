import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;


// This class contains the main logic for analyzing game data to detect faulty moves
public class Main {

    // Read game data from a file and return a list of Analyzer objects
    public static List<Analyzer> readGameData(String fileName) {
        List<Analyzer> gameData = new ArrayList<>();

        try (Scanner sc = new Scanner(new File(fileName))) {
            while (sc.hasNextLine()) {
                String row = sc.nextLine();
                // Split the input string into individual data fields
                String[] split = row.split(",");
                if (split.length != 6) {
                    continue;
                }
                // Extract timestamp, gameID, playerID, action, handDealer, and handPlayer from the input data
                int timestamp = Integer.parseInt(split[0]);
                int gameID = Integer.parseInt(split[1]);
                int playerID = Integer.parseInt(split[2]);
                String action = split[3];
                String handDealer = split[4];
                String handPlayer = split[5];

                // Create a new Analyzer object and add it to the gameData list
                gameData.add(new Analyzer(timestamp, gameID, playerID, action, handDealer, handPlayer));
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return gameData;
    }

     // Write a list of faulty moves to a file
    public static void writeFaultyMoves(List<Analyzer> faultyMoves, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Analyzer move : faultyMoves) {
                writer.println(move.getTimestamp() + "," + move.getGameID() + "," + move.getPlayerID() + "," + move.getAction() + "," + move.getHandDealer() + "," + move.getHandPlayer());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Analyze game data and return a list of faulty moves
    public static List<Analyzer> findFaultyMoves(List<Analyzer> gameData) {
        // Define a comparator to sort game data by gameID and timestamp
        Comparator<Analyzer> gameIDAndTimestampSort = Comparator.comparing(Analyzer::getGameID).thenComparing(Analyzer::getTimestamp);
        Collections.sort(gameData, gameIDAndTimestampSort);

        // Variables to keep track of the last move, timestamp and player's ID
        int lastGameID = gameData.get(0).getGameID();
        int lastTimestamp = 0;
        int lastPlayerID = gameData.get(0).getPlayerID();
        int rowNumber = 0;
        //true if the move is valid, false if the move is faulty
        boolean gameCondition;
        // Set to store game IDs that have already been marked as faulty
        Set<Integer> gameIDSet = new HashSet<>();
        // List to store faulty moves found during the analysis
        List<Analyzer> faultyMoves = new ArrayList<>();

        // Iterate through game data and check if each move meets the game requirements
        for (Analyzer analyzer : gameData) {
            rowNumber++;
            if (analyzer.getGameID() != lastGameID) {
                lastGameID = analyzer.getGameID();
                lastPlayerID = analyzer.getPlayerID();
                lastTimestamp = 0;
            }
            gameCondition = GameCondition.gameMeetsTheRequirements(analyzer, lastTimestamp, lastPlayerID, rowNumber, gameData, lastGameID);
            lastTimestamp = analyzer.getTimestamp();

            if (!gameCondition) {
                if (!gameIDSet.contains(analyzer.getGameID())) {
                    faultyMoves.add(analyzer);
                    gameIDSet.add(analyzer.getGameID());
                }
            }
        }
        return faultyMoves;
    }


    // Main method to run the game analysis
    public static void main(String[] args) {
        // Read game data from the input file
        List<Analyzer> gameData = readGameData("game_data_2");
        // Find faulty moves in the game data
        List<Analyzer> faultyMoves = findFaultyMoves(gameData);
        // Write faulty moves to the output file
        writeFaultyMoves(faultyMoves, "analyzer_output_2.txt");
    }
}