import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CardPack {
    List<Card> pack;

    public CardPack(List<Card> pack) {
        this.pack = pack;
    }

    public CardPack() {
        this.pack = createPack();
    }


    public List<Card> createPack(){
        List<Card> pack = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            for (int j = 2; j < 15; j++){
                pack.add(new Card(i,j));
            }
        }
        return pack;
    }

    public void shufflePack(){
        Collections.shuffle(this.pack);
    }

    public Card pickCard(){
        Card chosenCard = pack.get(0);
        pack.remove(0);

        return chosenCard;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : this.pack){
            sb.append(card.toString());
        }
        String cards = sb.toString();
        return "Cards in pack: " + cards;
    }
}
