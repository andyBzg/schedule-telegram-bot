package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

public class Bot extends TelegramLongPollingBot {

    private Storage storage;
    private ReplyKeyboardMarkup replyKeyboardMarkup;

    public Bot() {
        storage = new Storage();
        initKeyboard();
    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_NAME");
    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }

    @Override
    public void onUpdateReceived(Update update) {

        Message originalMessage = update.getMessage(); //Извлекаем из объекта сообщение пользователя
        System.out.println(originalMessage.getText()); //Вывод сообщения в консоль

        String chatID = originalMessage.getChatId().toString();
        //Получаем текст сообщения пользователя, отправляем обработчик
        String parserResponse = parseMessage(originalMessage.getText());

        //Добавляем в сообщение id чата а также ответ
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatID);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
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
        if (ServiceCommand.START.equals(textMsg) || textMsg.equals("Старт"))
            response = greetings();
        else if (ServiceCommand.INFO.equals(textMsg) || textMsg.equals("Info")) {
            return info();
        } else if (ServiceCommand.REQUIRED.equals(textMsg) || textMsg.equals("Обязательные"))
            response = storage.showAllLectures();
        else if (ServiceCommand.ELECTIVE.equals(textMsg) || textMsg.equals("Дополнительные"))
            response = storage.showElectiveLectures();
        else if (ServiceCommand.ADDITIONAL.equals(textMsg) || textMsg.equals("Консультации")) {
            response = storage.showAdditional();
        } else
            response = textMsg;

        return response;
    }

    private String greetings() {
        return """
                Приветствую!\s
                Бот показывает расписание лекций на текущую неделю\s
                Введите команду /required, чтобы посмотреть обязательные лекции\s
                /elective , чтобы посмотреть дополнительные и\s
                /additional , чтобы увидеть конусльтации""";
    }

    private String info() {
        return """
                Список доступных команд:\s
                /start\s
                /info\s
                /required\s
                /elective\s
                /additional""";
    }

    public void initKeyboard() {
        //Создаем объект будущей клавиатуры и выставляем нужные настройки
        replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        //Создаем список с рядами кнопок
        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        //Создаем один ряды кнопок и добавляем их в список
        KeyboardRow firstKeyboardRow = new KeyboardRow();
        KeyboardRow secondKeyboardRow = new KeyboardRow();
        KeyboardRow thirdKeyboardRow = new KeyboardRow();
        keyboardRows.add(firstKeyboardRow);
        keyboardRows.add(secondKeyboardRow);
        keyboardRows.add(thirdKeyboardRow);
        //Добавляем одну кнопки с текстом наши ряды
        firstKeyboardRow.add(new KeyboardButton("Старт"));
        firstKeyboardRow.add(new KeyboardButton("Info"));
        secondKeyboardRow.add(new KeyboardButton("Обязательные"));
        secondKeyboardRow.add(new KeyboardButton("Дополнительные"));
        thirdKeyboardRow.add(new KeyboardButton("Консультации"));
        //добавляем лист с одним рядом кнопок в главный объект
        replyKeyboardMarkup.setKeyboard(keyboardRows);
    }
}


