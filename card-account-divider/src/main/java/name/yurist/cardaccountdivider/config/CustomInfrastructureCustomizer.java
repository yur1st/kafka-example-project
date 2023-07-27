package name.yurist.cardaccountdivider.config;

import name.yurist.cardaccountdivider.domain.Account;
import name.yurist.cardaccountdivider.domain.AccountAggregated;
import name.yurist.cardaccountdivider.domain.Card;
import name.yurist.cardaccountdivider.domain.CardType;
import name.yurist.cardaccountdivider.dto.CardDto;
import name.yurist.cardaccountdivider.serde.JsonSerdes;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;

public class CustomInfrastructureCustomizer implements KafkaStreamsInfrastructureCustomizer {

    private final String inputCardTopic;
    private final String inputAccountTopic;
    private final String outputTopicPrefix;

    public CustomInfrastructureCustomizer(String inputCardTopic, String inputAccountTopic, String outputTopicPrefix) {
        this.inputCardTopic = inputCardTopic;
        this.inputAccountTopic = inputAccountTopic;
        this.outputTopicPrefix = outputTopicPrefix;
    }

    public void configureBuilder(StreamsBuilder builder) {

        KTable<String, AccountAggregated> accountAggregatedKTable = builder.stream(inputAccountTopic,
                        Consumed.with(Serdes.String(), JsonSerdes.AccountDto()))
                .map((k, v) -> new KeyValue<>(v.getAgrNo(), v))
                .groupByKey(Grouped.with(Serdes.String(), JsonSerdes.AccountDto()))
                .aggregate(AccountAggregated::new,
                        (key, value, agg) -> {
                            agg.setAgreNo(key);
                            agg.getAccounts().add(Account.from(value));
                            return agg;
                        }, Materialized.<String, AccountAggregated, KeyValueStore<Bytes, byte[]>>
                                as("AccountDto-MV")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.AccountAggregated()));

        KTable<String, CardDto> cardDtoKTable = builder.stream(inputCardTopic,
                Consumed.with(Serdes.String(), JsonSerdes.CardDto()))
                .map((k,v) -> new KeyValue<>(v.getAgrNo(), v))
                .toTable(Materialized.<String, CardDto, KeyValueStore<Bytes, byte[]>>
                        as("CardDto-MV")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.CardDto()));

        KTable<String, Card> cardKTable = cardDtoKTable.join(accountAggregatedKTable,
                Card::fromDtos,
                Materialized.<String, Card, KeyValueStore<Bytes, byte[]>>
                as("CardAccounts-MV")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(JsonSerdes.Card()));

        Produced<String, Card> cardProduced = Produced.with(Serdes.String(), JsonSerdes.Card());

        cardKTable.toStream()
                .map((k,v) -> new KeyValue<>(v.getNumber(), v))
                .split()
                .branch((k, v) -> CardType.BR.equals(v.getCardType()),
                        Branched.withConsumer(ks -> ks.to(outputTopicPrefix + "br", cardProduced)))
                .branch((k, v) -> CardType.MVC.equals(v.getCardType()),
                        Branched.withConsumer(ks -> ks.to(outputTopicPrefix + "mvc", cardProduced)))
                .branch((k, v) -> CardType.GL.equals(v.getCardType()),
                        Branched.withConsumer(ks -> ks.to(outputTopicPrefix + "gl", cardProduced)))
                ;
    }

}
