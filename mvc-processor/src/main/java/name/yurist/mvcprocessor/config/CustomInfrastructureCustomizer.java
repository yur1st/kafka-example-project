package name.yurist.mvcprocessor.config;

import name.yurist.mvcprocessor.domain.Card;
import name.yurist.mvcprocessor.domain.Person;
import name.yurist.mvcprocessor.joiner.ValueJoiners;
import name.yurist.mvcprocessor.serde.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;

public class CustomInfrastructureCustomizer implements KafkaStreamsInfrastructureCustomizer {

    private final String cardTopic;
    private final String clientsTopic;

    public CustomInfrastructureCustomizer(String cardTopic, String clientsTopic){
        this.cardTopic = cardTopic;
        this.clientsTopic = clientsTopic;
    }

    @Override
    public void configureBuilder(StreamsBuilder builder) {

        KTable<String, Card> cardDtoKTable = builder.stream(cardTopic,
                        Consumed.with(Serdes.String(), JsonSerdes.card()))
                .map((k, v) -> new KeyValue<>(v.getAgrNo(), v))
                .toTable(Materialized.<String, Card, KeyValueStore<Bytes, byte[]>>
                                as("CardDto-MV")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.card()));

        KStream<String, Person> personKStream = builder.stream(clientsTopic,
                        Consumed.with(Serdes.String(), JsonSerdes.personDto()))
                .mapValues(value -> new Person(value.getName(), value.getPhone()));

        personKStream.join(cardDtoKTable, ValueJoiners.cardPersonValueJoiner)
                .toTable(Materialized.<String, Card, KeyValueStore<Bytes, byte[]>>
                                as("CardPerson-MV")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.card()));
    }

}
