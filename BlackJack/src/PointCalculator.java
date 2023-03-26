// PointCalculator class is responsible for calculating points based on the cards
public class PointCalculator {
        // calculatePoints method takes a string of cards and returns the total points
        public static int calculatePoints(String cards) {
        int points = 0;

        // Split the input cards string into an array of individual cards
        String[] splitCards = cards.split("-");
        // Check for card validity and return -1 if any card is invalid
        for (String splittedCards : splitCards) {
            if (!(ErrorDetector.checkCards(splittedCards))) {
                return -1;
            }
        }
        // Calculate points for each card
        for (String card : splitCards) {
            char first = card.charAt(0);

            if (card.length() == 2) {
                if (Character.isLetter(first)) {
                    // If the card is an ace ('A'), it's worth 11 points
                    if (Character.toLowerCase(first) == 'a') {
                        points += 11;
                        // Other face cards ('K', 'Q', 'J') are worth 10 points
                    } else {
                        points += 10;
                    }
                    //For numbered cards add their numeric value to the points
                } else {
                    points += first - 48;
                }
            }
            // For cards with length 3 (e.g., '10H'), they are worth 10 points
            if (card.length() == 3) {
                points += 10;
            }
        }
        return points;
    }
}
