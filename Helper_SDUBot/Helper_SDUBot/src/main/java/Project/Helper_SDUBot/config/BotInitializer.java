package Project.Helper_SDUBot.config;

import Project.Helper_SDUBot.service.SHA_MainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@Component
public class BotInitializer {

    @Autowired
    SHA_MainService service;

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {

        TelegramBotsApi telegramBotsApi= new TelegramBotsApi(DefaultBotSession.class);

        try {
            telegramBotsApi.registerBot(service);
        } catch (TelegramApiException e) {
            log.error("An error: {}", e.getMessage());
        }
    }
}
