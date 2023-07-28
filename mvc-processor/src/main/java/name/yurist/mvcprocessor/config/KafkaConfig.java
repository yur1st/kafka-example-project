package name.yurist.mvcprocessor.config;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import java.util.Map;

import static java.util.Map.entry;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
public class KafkaConfig {

    private static final String CARD_TOPIC = "cards";
    private static final String CLIENTS_TOPIC = "clients";

    @Bean
    public KafkaStreamsConfiguration kafkaStreamsConfigConfiguration() {
        return new KafkaStreamsConfiguration(
                Map.ofEntries(
                        entry(APPLICATION_ID_CONFIG, "mvc-processor"),
                        entry(BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"),
                        entry(NUM_STREAM_THREADS_CONFIG, 1),
                        entry(consumerPrefix(SESSION_TIMEOUT_MS_CONFIG), 300000),
                        entry(consumerPrefix(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG), "earliest"),
                        // PROD CONFS
                        entry(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class),
                        entry(REPLICATION_FACTOR_CONFIG, 1),
                        entry(topicPrefix(TopicConfig.RETENTION_MS_CONFIG), Integer.MAX_VALUE),
                        entry(producerPrefix(ProducerConfig.ACKS_CONFIG), "all"),
                        entry(producerPrefix(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG), 2147483647),
                        entry(producerPrefix(ProducerConfig.MAX_BLOCK_MS_CONFIG), 9223372036854775807L)));
    }

    @Bean("streamsBuilderFactoryBean")
    @Primary
    public StreamsBuilderFactoryBean streamsBuilderFactoryBean(KafkaStreamsConfiguration kafkaStreamsConfigConfiguration)
            throws Exception {

        StreamsBuilderFactoryBean streamsBuilderFactoryBean =
                new StreamsBuilderFactoryBean(kafkaStreamsConfigConfiguration);
        streamsBuilderFactoryBean.afterPropertiesSet();
        streamsBuilderFactoryBean.setInfrastructureCustomizer(new CustomInfrastructureCustomizer(CARD_TOPIC, CLIENTS_TOPIC));
        streamsBuilderFactoryBean.setCloseTimeout(10); //10 seconds
        return streamsBuilderFactoryBean;
    }
}