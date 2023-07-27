package name.yurist.cardaccountdivider.serde;

import name.yurist.cardaccountdivider.domain.Account;
import name.yurist.cardaccountdivider.domain.AccountAggregated;
import name.yurist.cardaccountdivider.domain.Card;
import name.yurist.cardaccountdivider.dto.AccountDto;
import name.yurist.cardaccountdivider.dto.CardDto;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class JsonSerdes {

    public static Serde<Account> Account() {
        JsonSerializer<Account> serializer = new JsonSerializer<>();
        JsonDeserializer<Account> deserializer = new JsonDeserializer<>(Account.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
    public static Serde<AccountDto> AccountDto() {
        JsonSerializer<AccountDto> serializer = new JsonSerializer<>();
        JsonDeserializer<AccountDto> deserializer = new JsonDeserializer<>(AccountDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
    public static Serde<Card> Card() {
        JsonSerializer<Card> serializer = new JsonSerializer<>();
        JsonDeserializer<Card> deserializer = new JsonDeserializer<>(Card.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
    public static Serde<CardDto> CardDto() {
        JsonSerializer<CardDto> serializer = new JsonSerializer<>();
        JsonDeserializer<CardDto> deserializer = new JsonDeserializer<>(CardDto.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }

    public static Serde<AccountAggregated> AccountAggregated() {
        JsonSerializer<AccountAggregated> serializer = new JsonSerializer<>();
        JsonDeserializer<AccountAggregated> deserializer = new JsonDeserializer<>(AccountAggregated.class);
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
