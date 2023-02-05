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

        Long chatID = originalMessage.getChatId(); //получаем id чата из сообщения
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
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String parseMessage(String textMsg) {

        String response;
        //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
        if (ServiceCommand.START.equals(textMsg))
            response = greetings();
        else if (ServiceCommand.HELP.equals(textMsg)) {
            response = info();
        } else if (ServiceCommand.REQUIRED.equals(textMsg))
            response = storage.showRequiredLectures();
        else if (ServiceCommand.ELECTIVE.equals(textMsg))
            response = storage.showElectiveLectures();
        else if (ServiceCommand.CONSULTATION.equals(textMsg)) {
            response = storage.showConsultations();
        } else
            response = textMsg;

        return response;
    }

    private String greetings() {
        return """
                Привет!\s
                \nБот умеет показывать расписание лекций и отвечать на сообщение таким же сообщением =)\s
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
        commandList.add(new BotCommand(ServiceCommand.START.toString(), "get a welcome message"));
        commandList.add(new BotCommand(ServiceCommand.REQUIRED.toString(), "get required lectures"));
        commandList.add(new BotCommand(ServiceCommand.ELECTIVE.toString(), "get elective lectures"));
        commandList.add(new BotCommand(ServiceCommand.CONSULTATION.toString(), "get consultations"));
        commandList.add(new BotCommand(ServiceCommand.HELP.toString(), "show all commands"));
        try{
            this.execute(new SetMyCommands(commandList, new BotCommandScopeDefault(), null));
        }
        catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}


