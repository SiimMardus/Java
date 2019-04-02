/*
Card game "Linnade põletamine" simulator
Run simulateGames(speed, totalGames) in main class

Game rules:
1. Deck gets shuffled and split between players.
2. Players draw the top card of the deck.
3. Player with the lower card has to take both cards into their graveyard
4. In case where both cards have the same strength, players place one card face down, and another face up for another round
5. When a player runs out of cards in the main deck, they graveyard gets shuffled and used as a main deck again
6. The first player to get rid of all cards is the winner.
 */


import java.util.*;

public class Game {

    public static void main(String[] args) throws InterruptedException{

        simulateGames(1, 5);

    }

    /**
     *
     * @param speed - The wait between card draws, rounds and outcomes in milliseconds. Smaller speed = faster games
     * @param totalGames - Total amount of games to simulate
     */
    public static void simulateGames(int speed, int totalGames) throws InterruptedException{

        HashMap<String, Integer> results  = new HashMap<>();
        results.put("A", 0);
        results.put("B", 0);

        String gameOutcome;
        int rounds;
        String winner;


        for (int i = 1; i <= totalGames; i += 1){

            gameOutcome = linnadePõletamine(speed);
            rounds = Integer.parseInt(gameOutcome.substring(1));
            winner = gameOutcome.substring(0,1);

            if (winner.equals("A")){
                results.replace("A", results.get("A") + 1);
            } else {
                results.replace("B", results.get("B") + 1);
            }

            System.out.println("FInished a game with " + rounds + " rounds! Winner: " + winner + "!");
        }


        System.out.println("Played total of " + results.get("A") + results.get("B") + " game(s).\n" +
                "Number of wins:");
        System.out.println(results);
    }


    /**
     * This method shuffles the grave pack and makes it a main pack if needed.
     * @param pack - main pack
     * @param gravePack - grave pack
     * @return - returns both packs in a list
     */
    public static List<CardPack> packCheck(CardPack pack, CardPack gravePack){

        List<CardPack> packs = new ArrayList<>();
        if (pack.pack.size() <= 0){
            gravePack.shufflePack();
            pack = new CardPack(gravePack.pack);
            gravePack = new CardPack(new ArrayList<>());
        }

        packs.add(pack);
        packs.add(gravePack);

        return packs;
    }

    /**
     * @param speed - The wait between card draws, rounds and outcomes in milliseconds. Smaller speed = faster games
     * @return - Returns the game outcome (winner + rounds played) as a string. i.e A322 = Player A won after 322 rounds
     */
    public static String linnadePõletamine(int speed) throws InterruptedException{

        // Creating a new pack, shuffling it and splitting it between player A and B
        CardPack newPack = new CardPack();
        newPack.shufflePack();

        List<Card> firstHalf = new ArrayList<>(newPack.pack.subList(0,26));
        List<Card> secondHalf = new ArrayList<>(newPack.pack.subList(26,52));

        CardPack pack1 = new CardPack(firstHalf);
        CardPack pack2 = new CardPack(secondHalf);

        // Creating empty packs for graveyards
        CardPack gravePack1 = new CardPack(new ArrayList<>());
        CardPack gravePack2 = new CardPack(new ArrayList<>());
        pack1.shufflePack();
        pack2.shufflePack();

        // Initializing values for the game
        String roundWinner = "";
        List<Card> warBuffer = new ArrayList<>();
        boolean wasWar = false;
        int warsize = 0;
        List<CardPack> checkPacks;
        int rounds = 0;

        // Game loop
        while (true){

            // Increasing the round count
            rounds++;

            // Both players pick a card
            Card card1 = pack1.pickCard();
            Card card2 = pack2.pickCard();


            System.out.println("Player A drew: " + card1);
            Thread.sleep(speed);
            System.out.println("Player B drew: " + card2);
            Thread.sleep(speed);


            // Checking the winner
            if (card1.getStrength() > card2.getStrength()){
                roundWinner = "A";
                gravePack2.pack.add(card1);
                gravePack2.pack.add(card2);
            } else if (card2.getStrength() > card1.getStrength()){
                roundWinner = "B";
                gravePack1.pack.add(card1);
                gravePack1.pack.add(card2);
            } else {
                // War = Both players played a card with same strength
                // Both of them sacrifice one card (Place it face down on the previous cards) and then play another one

                wasWar = true;
                warsize = 0;

                // Adding the first cards to warbuffer, which is picked up by the loser after war
                warBuffer.add(card1);
                checkPacks = packCheck(pack1, gravePack1);
                pack1 = checkPacks.get(0);
                gravePack1 = checkPacks.get(1);

                warBuffer.add(card2);
                checkPacks = packCheck(pack2, gravePack2);
                pack2 = checkPacks.get(0);
                gravePack2 = checkPacks.get(1);

                while (card1.getStrength() == card2.getStrength()){

                    System.out.println("WAR!");
                    Thread.sleep(speed);

                    // If either one of the players would get rid of their pack after the sacrifice, they win.
                    if (pack1.pack.size() + gravePack1.pack.size() < 2){

                        roundWinner = "A";
                        warBuffer.addAll(pack1.pack);
                        warBuffer.addAll(gravePack1.pack);
                        gravePack2.pack.addAll(warBuffer);
                        warBuffer.clear();
                        break;
                    }
                    if (pack2.pack.size() + gravePack2.pack.size() < 2){

                        roundWinner = "B";
                        warBuffer.addAll(pack2.pack);
                        warBuffer.addAll(gravePack2.pack);
                        gravePack1.pack.addAll(warBuffer);
                        warBuffer.clear();
                        break;
                    }

                    // Sacrifices
                    System.out.println("Both players sacrifice one card!");
                    Thread.sleep(speed);
                    Card backCard1 = pack1.pickCard();
                    warBuffer.add(backCard1);
                    checkPacks = packCheck(pack1, gravePack1);
                    pack1 = checkPacks.get(0);
                    gravePack1 = checkPacks.get(1);

                    Card backCard2 = pack2.pickCard();
                    warBuffer.add(backCard2);
                    checkPacks = packCheck(pack2, gravePack2);
                    pack2 = checkPacks.get(0);
                    gravePack2 = checkPacks.get(1);

                    // Second cards
                    card1 = pack1.pickCard();
                    warBuffer.add(card1);
                    checkPacks = packCheck(pack1, gravePack1);
                    pack1 = checkPacks.get(0);
                    gravePack1 = checkPacks.get(1);

                    card2 = pack2.pickCard();
                    warBuffer.add(card2);
                    checkPacks = packCheck(pack2, gravePack2);
                    pack2 = checkPacks.get(0);
                    gravePack2 = checkPacks.get(1);

                    System.out.println("Player A drew: " + card1);
                    Thread.sleep(speed);
                    System.out.println("Player B drew: " + card2);
                    Thread.sleep(speed);

                    warsize = warBuffer.size();

                    // Loser picks up the cards
                    if (card1.getStrength() > card2.getStrength()){
                        roundWinner = "A";

                        gravePack2.pack.addAll(warBuffer);

                        warBuffer.clear();
                        break;
                    } else if (card2.getStrength() > card1.getStrength()){
                        roundWinner = "B";

                        gravePack1.pack.addAll(warBuffer);

                        warBuffer.clear();
                        break;
                    }
                }
            }

            // Announcement after war
            if (wasWar){
                wasWar = false;
                System.out.println("War ended! Player " + roundWinner + " wins!");
                Thread.sleep(speed);
                if (roundWinner.equals("A")){
                    System.out.println("Player B picked up " + warsize + " cards!");
                } else {
                    System.out.println("Player A picked up " + warsize + " cards!");
                }

            } else { // Announcement after a regular round
                System.out.println("Player " + roundWinner + " wins!");
                Thread.sleep(speed);
            }

            // Information about remaining cards
            System.out.println("Cards left:" );

            int cardsLeftA = pack1.pack.size() + gravePack1.pack.size();
            int cardsLeftB = pack2.pack.size() + gravePack2.pack.size();
            System.out.println("A: " + cardsLeftA);
            System.out.println("B: " + cardsLeftB);
            Thread.sleep(speed);

            // Checking the packs in case a switch is needed
            checkPacks = packCheck(pack1, gravePack1);
            pack1 = checkPacks.get(0);
            gravePack1 = checkPacks.get(1);

            checkPacks = packCheck(pack2, gravePack2);
            pack2 = checkPacks.get(0);
            gravePack2 = checkPacks.get(1);

            // If either of the players has no cards left in their deck, they win
            if (cardsLeftA == 0){
                System.out.println("Player A wins! " + rounds + " rounds played!");
                return "A" + Integer.toString(rounds);
            } else if (cardsLeftB == 0){
                System.out.println("Player B wins! " + rounds +" rounds played!");
                return "B" + Integer.toString(rounds);
            }
        }
    }
}
