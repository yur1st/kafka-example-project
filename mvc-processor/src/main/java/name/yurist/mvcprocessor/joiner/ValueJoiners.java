package name.yurist.mvcprocessor.joiner;

import name.yurist.mvcprocessor.domain.Card;
import name.yurist.mvcprocessor.domain.Person;
import org.apache.kafka.streams.kstream.ValueJoiner;

public class ValueJoiners {

    private ValueJoiners() {throw new IllegalStateException("Utility class");}

    public static final ValueJoiner<Person, Card, Card> cardPersonValueJoiner =
            (person, card) -> new Card(card.getNumber(), card.getCardType(), card.getAgrNo(), card.getAccounts(), person);
}
