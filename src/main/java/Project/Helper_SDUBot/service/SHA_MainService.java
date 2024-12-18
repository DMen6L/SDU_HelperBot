package Project.Helper_SDUBot.service;

import Project.Helper_SDUBot.config.BotAnswer;
import Project.Helper_SDUBot.config.QandA;
import Project.Helper_SDUBot.config.SHA_MainConfig;
import Project.Helper_SDUBot.config.helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SHA_MainService extends TelegramLongPollingBot {
    //Values for Bot Service
    private final SHA_MainConfig config; //User configuration, the bot name and API
    private QandA qanda; //Questions and Answers class that will depend on the language
    private Map<String, Object> QandAs; //Questions and answers themselves
    private Map<String, Boolean> userAskMode = new HashMap<>(); //Saving and registering whether if the user activates ask mode
    private String language; //User's language

    //Constructor: gives bot token to TelegramLongPollingBot class and registers users configuration
    public SHA_MainService(SHA_MainConfig config) {
        super(config.getToken()); //Giving bot token to super class(TelegramLongPollingBot)
        this.config = config;
    }

    //Overriding 1/2 main TelegramLongPollingBot functions(getBotUserName)
    @Override
    public String getBotUsername() { return this.config.getBotName(); }

    //Overriding 2/2 main TelegramLongPollingBot functions(does all of the main work)
    @Override
    public void onUpdateReceived(Update update) {
        //Check if user has written or pressed anything
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText(); //Getting users text
            long chatId = update.getMessage().getChatId(); //Getting main chatId

            //If language is not chosen automatically prompts user to chose one
             if(messageText.equals("/start")) {
                startCommand(chatId, update.getMessage().getChat().getFirstName()); //A command prompting user to choose a language
                sendReplyKeyboardMarkup(chatId); //Creating a choosing keyboard
            } else if(messageText.equals("English") || messageText.equals("Russian") || messageText.equals("Kazakh")) {
                 this.language = messageText; //Changes or sets language
                 qanda = new QandA(this.language); //Getting JSON file broken down into Map for all questions and answers
                 QandAs = qanda.getQandAs(); //Getting all of the questions and answers into local Map
                 sendMessage(chatId, "You selected " + this.language); //Sending which language the user has chosen
             } else if(this.language.isBlank()) {
                 sendMessage(chatId, "First choose a language: ");
                 sendReplyKeyboardMarkup(chatId); //creates choosing poll for languages
             } else if(messageText.equals("Cancel")) {
                userAskMode.clear(); //clearing state of user ask mode
                userAskMode.put(String.valueOf(chatId), false); //putting information that user is no longer in ask mode
                clearReplyKeyboard(chatId, "Ask is canceled"); //When in ask mode, "cancel" deactivates it
            } else if(userAskMode.getOrDefault(String.valueOf(chatId), false)) {
                BotAnswer bt = new BotAnswer();
                sendMessage(chatId, bt.getAnswer(QandAs, messageText));
            } else if(messageText.equals("/help")) {
                helpCommand(chatId); //When help is pressed it shows all commands
            } else if(messageText.equals("/ask")) {
                userAskMode.put(String.valueOf(chatId), true); //Activates user ask mode
                askCommand(chatId); //Initialize everything needed for ask mode
            } else if(messageText.equals("/Show_All_Questions")) {
                //Print out all the questions and answers
                QandAs.forEach((k, v)-> {
                    sendMessage(chatId, k + " : " + v);
                });
            } else if(messageText.equals("/moodle_sdu")){
                sendMessage(chatId, "https://moodle.sdu.edu.kz/login/index.php");
            } else if(messageText.equals("/my_sdu")) {
                 sendMessage(chatId, "https://my.sdu.edu.kz/index.php");
             }else {
                sendMessage(chatId, "I don't know this command:("); //If user tries to use non existing command
            }
        }
    }

    //Main command for starting the bot
    private void startCommand(long chatId, String name) {
        String answer = "Hi " + name + ", nice to meet you!"; //Just texting welcome message

        log.info("Answered to user"); //Logging that the user has gotten the welcome

        sendMessage(chatId, answer); //Sending the welcome message
    }

    //If /help is chosen as a function
    private void helpCommand(long chatId) {
        //try cause work with files can cause errors
        try {
            helper help = new helper(this.language); //Getting help commands from JSON

            Map<String, Object> helpCommands = help.getHelps(); //Writing all commands into a map

            //Printing them out
            helpCommands.forEach((k, v)->{
                sendMessage(chatId, k + ": " + v);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //When ask mode is activated
    private void askCommand(long chatId) {
        SendMessage message = new SendMessage(); //Preparing message to send
        message.setChatId(chatId);
        message.setText("You can now ask questions. Press 'Cancel' to stop.");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //creating a markup keyboard
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboard = new ArrayList<>(); //Creating a keyboard itself
        KeyboardRow keyboardRow = new KeyboardRow(); //Creating a row for it

        keyboardRow.add("Cancel"); //Adding "Cancel button
        keyboard.add(keyboardRow); //Adding it to the markup keyboard

        replyKeyboardMarkup.setKeyboard(keyboard); //Adding to the reply markup keuyboard

        message.setReplyMarkup(replyKeyboardMarkup); //And to the interface

        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    //Poll of languages
    private void sendReplyKeyboardMarkup(long chatId) {
        SendMessage message = new SendMessage(); //Creating initial message
        message.setChatId(chatId);
        message.setText("Please select your language:");

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(); //Creating the poll
        ArrayList<KeyboardRow> replyKeyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow(); //Adding first two languages
        row1.add("English");
        row1.add("Russian");

        KeyboardRow row2 = new KeyboardRow(); //Adding Kazakh language option
        row2.add("Kazakh");

        //Adding them to the keyboard
        replyKeyboard.add(row1);
        replyKeyboard.add(row2);

        replyKeyboardMarkup.setKeyboard(replyKeyboard); //Setting the keyboard
        replyKeyboardMarkup.setResizeKeyboard(true); //It now changes automatically depending on display size
        replyKeyboardMarkup.setOneTimeKeyboard(true); //It is shown only once
        message.setReplyMarkup(replyKeyboardMarkup); //Message is used to set the poll

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    //Clearing the buttons for the keyboard
    private void clearReplyKeyboard(long chatId, String _message) {
        SendMessage message = new SendMessage(); //Creating initial deleting message
        message.setChatId(chatId);
        message.setText(_message);

        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove(); //Special type for removal
        replyKeyboardRemove.setRemoveKeyboard(true);

        message.setReplyMarkup(replyKeyboardRemove); //Binding remove command to the message

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    //Command for sending a message
    private void sendMessage(long chatId, String ans) {
        SendMessage message = new SendMessage(); //Creating message
        message.setChatId(String.valueOf(chatId));
        message.setText(ans);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("An error: {}", e.getMessage());
        }
    }
}
