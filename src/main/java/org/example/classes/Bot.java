package org.example.classes;

import org.example.enums.Buttons;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class Bot extends TelegramLongPollingBot {

    private final String BOT_NAME = System.getenv("BOT_NAME");
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
    static final String START_COMMAND = "/start";
    private Storage storage;


    public Bot() {
        storage = new Storage();
        initCommands();
    }


    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            System.out.println(update.getMessage().getText());
        } //Вывод сообщения в консоль

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatID = update.getMessage().getChatId();

            if (messageText.equals(START_COMMAND) || messageText.equals(START_COMMAND + "@" + getBotUsername())) {
                String response = greetings();
                inlineButtons(response, chatID);
            }

        }
        else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageID = update.getCallbackQuery().getMessage().getMessageId();
            long chatID = update.getCallbackQuery().getMessage().getChatId();

            String editedText;

            if (callbackData.equals(Buttons.REQUIRED_BUTTON.toString())) {
                editedText = storage.showRequiredLectures();
            }
            else if (callbackData.equals(Buttons.ELECTIVE_BUTTON.toString())) {
                editedText = storage.showElectiveLectures();
            }
            else if (callbackData.equals(Buttons.CONSULT_BUTTON.toString())) {
                editedText = storage.showConsultations();
            }
            else {
                editedText = greetings();
            }

            EditMessageText message = new EditMessageText();
            message.setChatId(chatID);
            message.setText(editedText);
            message.setMessageId((int) messageID);

            sendEditedMessage(message);
        }
    }

    public void sendAnswerMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEditedMessage(EditMessageText message) {
        if (message != null) {
            try {
                execute(message);
            }
            catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    private void inlineButtons(String msgText, long chatID) {

        SendMessage message = new SendMessage();
        message.setText(msgText);
        message.setChatId(chatID);

        InlineKeyboardMarkup markupInLine = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> firstRowInLine = new ArrayList<>();
        List<InlineKeyboardButton> secondRowInLine = new ArrayList<>();

        InlineKeyboardButton requiredButton = new InlineKeyboardButton();
        requiredButton.setText(Buttons.REQUIRED_BUTTON_TEXT.toString());
        requiredButton.setCallbackData(Buttons.REQUIRED_BUTTON.toString());

        InlineKeyboardButton electiveButton = new InlineKeyboardButton();
        electiveButton.setText(Buttons.ELECTIVE_BUTTON_TEXT.toString());
        electiveButton.setCallbackData(Buttons.ELECTIVE_BUTTON.toString());

        InlineKeyboardButton consultationsButton = new InlineKeyboardButton();
        consultationsButton.setText(Buttons.CONSULT_BUTTON_TEXT.toString());
        consultationsButton.setCallbackData(Buttons.CONSULT_BUTTON.toString());

        firstRowInLine.add(requiredButton);
        firstRowInLine.add(electiveButton);
        secondRowInLine.add(consultationsButton);

        rowsInLine.add(firstRowInLine);
        rowsInLine.add(secondRowInLine);

        markupInLine.setKeyboard(rowsInLine);
        message.setReplyMarkup(markupInLine);

        sendAnswerMessage(message);
    }


    private String greetings() {
        return """
                Привет!\s
                \nЯ показываю расписание\s
                \nКакие лекции надо показать?""";
    }

    private void initCommands() {
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand(START_COMMAND, "get a welcome message"));

        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


