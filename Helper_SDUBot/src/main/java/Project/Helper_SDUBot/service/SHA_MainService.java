package Project.Helper_SDUBot.service;

import Project.Helper_SDUBot.config.SHA_MainConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class SHA_MainService extends TelegramLongPollingBot {

    final SHA_MainConfig config;

    String language;

    public SHA_MainService(SHA_MainConfig config) {

        super(config.getToken());
        this.config = config;
    }

    @Override
    public String getBotUsername() { return this.config.getBotName(); }

    @Override
    public void onUpdateReceived(Update update) {

        if(update.hasMessage() && update.getMessage().hasText()) {

            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch(messageText){
                case "/start":
                    startCommand(chatId, update.getMessage().getChat().getFirstName());
                    sendReplyKeyboardMarkup(chatId);
                    break;
                case "English":
                case "Russian":
                case "Kazakh":
                    this.language = messageText;
                    sendMessage(chatId, "You selected " + this.language);
                    break;
                default:
                    sendMessage(chatId, "I don't know this command:(");
                    break;
            }
        }
    }

    private void startCommand(long chatId, String name) {

        String answer = "Hi " + name + ", nice to meet you!";

        log.info("Answered to user");

        sendMessage(chatId, answer);
    }

    private void sendReplyKeyboardMarkup(long chatId) {

        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Please select your language:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        ArrayList<KeyboardRow> replyKeyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add("English");
        row1.add("Russian");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Kazakh");

        replyKeyboard.add(row1);
        replyKeyboard.add(row2);

        replyKeyboardMarkup.setKeyboard(replyKeyboard);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        message.setReplyMarkup(replyKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void sendMessage(long chatId, String ans) {

        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(ans);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("An error: {}", e.getMessage());
        }
    }
}
