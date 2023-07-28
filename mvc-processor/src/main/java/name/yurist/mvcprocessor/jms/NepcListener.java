package name.yurist.mvcprocessor.jms;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import name.yurist.mvcprocessor.domain.Card;
import name.yurist.mvcprocessor.domain.Nepc;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class NepcListener {

    private ReadOnlyKeyValueStore<String, Card> cardStore;
    private final InteractiveQueryService interactiveQueryService;

    public NepcListener(InteractiveQueryService interactiveQueryService) {
        this.interactiveQueryService = interactiveQueryService;
    }

    @PostConstruct
    private void initStore() throws InterruptedException {
        //TODO Fix store. Не инициализируется хранилище
        boolean flag = true;
        while (flag) {
            try {
                cardStore = interactiveQueryService.getQueryableStore("CardPerson-MV", QueryableStoreTypes.keyValueStore());
                flag = false;
            } catch (IllegalStateException ignored) {
                // store not yet ready for querying
                log.info("waiting for store readyness");
                Thread.sleep(1000);
            }
        }
    }

    @KafkaListener(topics = "nepc")
    public void onMessage(Nepc nepc) {
        if (cardStore.get(nepc.getNumber()) != null) {
            log.info("Непс принят: {}", nepc);
        } else {
            log.info("Hепс отклонен");
        }

    }

}
