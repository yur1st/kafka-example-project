package name.yurist.datagenerator.listeners;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import name.yurist.datagenerator.config.AppConfig;
import name.yurist.datagenerator.service.GeneratorService;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppListener {

    private final GeneratorService generatorService;
    private final AppConfig appConfig;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson = new Gson();

    public AppListener(GeneratorService generatorService, AppConfig appConfig, KafkaTemplate<String, String> kafkaTemplate) {
        this.generatorService = generatorService;
        this.appConfig = appConfig;
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener({ApplicationStartedEvent.class})
    public void handleContextStartEvent() {
        log.info("Start generating data");
        generatorService.generateCards(appConfig.getCardQuantity())
                .forEach(card -> kafkaTemplate.send("cards", card.getNumber(), gson.toJson(card)));
        generatorService.generateAccount(appConfig.getAccountQuantity())
                .forEach(account -> kafkaTemplate.send("accounts", account.getNumber(), gson.toJson(account)));
    }
}
