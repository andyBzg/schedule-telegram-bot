package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

import static org.example.ServiceCommand.*;

public class Bot extends TelegramLongPollingBot {

    private final String BOT_NAME = System.getenv("BOT_NAME");
    private final String BOT_TOKEN = System.getenv("BOT_TOKEN");
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

        Message originalMessage = update.getMessage(); //Извлекаем из объекта сообщение пользователя
        System.out.println(originalMessage.getText()); //Вывод сообщения в консоль

        String chatID = originalMessage.getChatId().toString(); //получаем id чата из сообщения
        //Получаем текст сообщения пользователя, отправляем обработчик
        String parserResponse = parseMessage(originalMessage.getText());

        //Добавляем в сообщение id чата а также ответ
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setText(parserResponse);
        sendAnswerMessage(sendMessage);
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

    public String parseMessage(String textMsg) {

        String response;
        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (START.equals(textMsg) || textMsg.equals(START + "@" + getBotUsername())) {
            response = greetings();
        }
        else if (HELP.equals(textMsg) || textMsg.equals(HELP + "@" + getBotUsername())) {
            response = info();
        }
        else if (REQUIRED.equals(textMsg) || textMsg.equals(REQUIRED + "@" + getBotUsername())) {
            response = storage.showRequiredLectures();
        }
        else if (ELECTIVE.equals(textMsg) || textMsg.equals(ELECTIVE + "@" + getBotUsername())) {
            response = storage.showElectiveLectures();
        }
        else if (CONSULTATION.equals(textMsg) || textMsg.equals(CONSULTATION + "@" + getBotUsername())) {
            response = storage.showConsultations();
        }
        else
            response = null;

        return response;
    }

    private String greetings() {
        return """
                Привет!\s
                \nБот умеет показывать расписание лекций\s
                \nЧтобы посмотреть список доступных команд воспользуйтесь меню или введите /help""";
    }

    private String info() {
        return """
                Список доступных команд:\s
                /start\s
                /help\s
                /required\s
                /elective\s
                /consultation""";
    }

    private void initCommands() {
        List<BotCommand> commandList = new ArrayList<>();
        commandList.add(new BotCommand(START.toString(), "get a welcome message"));
        commandList.add(new BotCommand(REQUIRED.toString(), "get required lectures"));
        commandList.add(new BotCommand(ELECTIVE.toString(), "get elective lectures"));
        commandList.add(new BotCommand(CONSULTATION.toString(), "get consultations"));
        commandList.add(new BotCommand(HELP.toString(), "show all commands"));

        try {
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


