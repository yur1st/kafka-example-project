package name.yurist.mvcprocessor.serde;


import name.yurist.mvcprocessor.domain.Account;
import name.yurist.mvcprocessor.domain.Card;
import name.yurist.mvcprocessor.domain.Nepc;
import name.yurist.mvcprocessor.domain.Person;
import name.yurist.mvcprocessor.dto.PersonDto;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerdes {

    private JsonSerdes() {throw new IllegalStateException("Utility class");}

    public static Serde<Account> account() {
        JsonSerializer<Account> serializer = new JsonSerializer<>();
        JsonDeserializer<Account> deserializer = new JsonDeserializer<>(Account.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Card> card() {
        JsonSerializer<Card> serializer = new JsonSerializer<>();
        JsonDeserializer<Card> deserializer = new JsonDeserializer<>(Card.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<PersonDto> personDto() {
        JsonSerializer<PersonDto> serializer = new JsonSerializer<>();
        JsonDeserializer<PersonDto> deserializer = new JsonDeserializer<>(PersonDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Person> person() {
        JsonSerializer<Person> serializer = new JsonSerializer<>();
        JsonDeserializer<Person> deserializer = new JsonDeserializer<>(Person.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<Nepc> nepc() {
        JsonSerializer<Nepc> serializer = new JsonSerializer<>();
        JsonDeserializer<Nepc> deserializer = new JsonDeserializer<>(Nepc.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
