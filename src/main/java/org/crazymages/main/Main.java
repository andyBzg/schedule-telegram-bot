package org.crazymages.main;

import lombok.extern.log4j.Log4j2;
import org.crazymages.classes.Bot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Log4j2
public class Main {
    public static void main(String[] args) {

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
            log.info("App started");
        } catch (TelegramApiException e) {
            log.error("Error occurred " + e.getMessage());
        }

    }
}