public class Card {
    int strength;
    int suit;

    public Card(int suit, int strength) {
        this.strength = strength;
        this.suit = suit;
    }

    public String cardSuit(){
        switch (suit){
            case 1:
                return "Hearts";
            case 2:
                return "Diamongs";
            case 3:
                return "Clubs";
            case 4:
                return "Spades";
        }
        return "";
    }

    @Override
    public String toString() {
        String strengthString = Integer.toString(strength);
        switch (strengthString){
            case "14":
                strengthString = "Ace";
                break;
            case "13":
                strengthString = "King";
                break;
            case "12":
                strengthString = "Queen";
                break;
            case "11":
                strengthString = "Jack";
                break;
        }
        return "[" + cardSuit() + " " + strengthString + "]";
    }

    public int getStrength() {
        return strength;
    }

}
