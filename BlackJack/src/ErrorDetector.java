import java.util.ArrayList;
import java.util.List;

// ErrorDetector class is responsible for validating game conditions and detecting errors in the game state
public class ErrorDetector {
     // checkGameOutcome method validates if the game outcome is correct based on the points of the player and the dealer
    public static int checkGameOutcome(Analyzer analyzer, int pointsPlayer, int pointsDealer) {
        // Check if the player won or the dealer lost
        if (analyzer.getAction().equals("P Win") || analyzer.getAction().equals("D Lose")) {
            if (!(pointsPlayer >= pointsDealer)) {
                return -1;
            }
            if (pointsDealer < 17) {
                return -1;
            }
        }
        // Check if the dealer won or the player lost
        if (analyzer.getAction().equals("D Win") || analyzer.getAction().equals("P Lose")) {
            if (!(pointsDealer > pointsPlayer)) {
                return -1;
            }
        }
        return 1;
    }

    // checkGameStart method validates if the game has started correctly
    public static int checkGameStart(Analyzer analyzer) {
        String dealerCards = analyzer.getHandDealer();
        if (analyzer.getAction().equals("P Joined") || analyzer.getAction().equals("D Readeal")) {
            String[] cards = dealerCards.split("-");
            // Check if the dealer has two cards and at least one of them is hidden
            if (cards.length == 2 && (cards[0].equals("?") || cards[1].equals("?"))) {
                return 1;
            }
        }
        return -1;
    }

    // checkCards method checks if the cards in a hand are valid
    public static boolean checkCards(String cards) {
        List<String> lst = new ArrayList<>();
        int hiddenCards = 0;
        String[] splitCards = cards.split("-");
        for (String card : splitCards) {
            String[] splitCharacters = card.split("");
            char firstChar = card.charAt(0);

            // Check if there is only one hidden card
            if (splitCharacters.length == 1) {
                if (!(firstChar == '?')) {
                    return false;
                } else {
                    hiddenCards++;
                }
            }

            if (hiddenCards > 1) {
                return false;
            }


            // Check if the card is valid for length 2
            if (splitCharacters.length == 2) {
                char secondChar = card.charAt(1);
                // Check if the card suit is valid
                if (!(Character.toLowerCase(secondChar) == 's' || Character.toLowerCase(secondChar) == 'h' || Character.toLowerCase(secondChar) == 'c' || Character.toLowerCase(secondChar) == 'd')) {
                    return false;
                }
                // Check if the card value is valid for digits
                if (Character.isDigit(firstChar)) {
                    if (!(firstChar >= 2)) {
                        return false;
                    }
                }
                // Check if the card value is valid for letters
                if (Character.isLetter(firstChar)) {
                    if (!(Character.toLowerCase(firstChar) == 'k' || Character.toLowerCase(firstChar) == 'q' || Character.toLowerCase(firstChar) == 'j')) {
                        return false;
                    }
                }
            }

            // Check if the card is valid for length 3
            if (splitCharacters.length == 3) {
                char secondChar = card.charAt(1);
                if (!(firstChar == '1' && secondChar == '0')) {
                    return false;
                }
            }

            if (splitCharacters.length > 3) {
                return false;
            }
            // Check for duplicates
            if (lst.contains(card.toLowerCase())) {
                return false;
            } else {
                lst.add(card.toLowerCase());
            }
        }
        return true;
    }

    //Checks if the dealer and player hands contain the same card(s).
    public static int checkSameCards(String dealerHand, String playerHand) {
        List<String> lst = new ArrayList<>();

        String[] splitDealerHand = dealerHand.split("-");
        for (String dealerCards : splitDealerHand) {
            if (lst.contains(dealerCards.toLowerCase())) {
                return -1;
            } else {
                lst.add(dealerCards.toLowerCase());
            }
        }
        String[] splitPlayerHand = playerHand.split("-");
        for (String playerCards : splitPlayerHand) {
            if (lst.contains(playerCards.toLowerCase())) {
                return -1;
            } else {
                lst.add(playerCards.toLowerCase());
            }
        }
        return 1;
    }


    //Checks if a given action is valid based on the current and next dealer and player hands.
    public static int checkAction(String action, String dealerCards, String playerCards, String nextDealerCards, String nextPlayerCards) {
        // Split the hands into arrays of cards
        String[] splitDealerCards = dealerCards.split("-");
        String[] splitPlayerCards = playerCards.split("-");
        String[] splitNextDealerCards = nextDealerCards.split("-");
        String[] splitNextPlayerCards = nextPlayerCards.split("-");
        // Get the count of cards in each hand
        int dealerCardsCount = splitDealerCards.length;
        int playerCardsCount = splitPlayerCards.length;
        int nextDealerCardsCount = splitNextDealerCards.length;
        int nextPlayerCardsCount = splitNextPlayerCards.length;

        // If the action is one of the following then we do not have to check them because we have already done it with other methods
        if (action.equals("P Win") || action.equals("D Win") || action.equals("D Redeal") || action.equals("P Lose") || action.equals("D Lose") || action.equals("P Joined") || action.equals("P Left")) {
            return 1;
        }

         // Check if the action is a "Player Hit" and if the player has only added one card to their hand
        if (action.equals("P Hit")) {
            if (nextPlayerCardsCount - playerCardsCount == 1) {
                return 1;
            }
        }

        // Check if the action is a "Dealer Hit" and if the dealer has only added one card to their hand
        if (action.equals("D Hit")) {
            if (nextDealerCardsCount - dealerCardsCount == 1) {
                return 1;
            }
        }

         // Check if the action is a "Dealer Show" and if the dealer has revealed their second card
        if (action.equals("D Show")) {
            if (splitDealerCards[1].equals("?") && !splitNextDealerCards[1].equals("?")) {
                return 1;
            }
        }
        // Check if the action is a "Player Stand" and if the player's hand count has not changed
        if (action.equals("P Stand")) {
            if (playerCardsCount == nextPlayerCardsCount) {
                return 1;
            }
        }

        return -1;
    }
}
