package org.example.engine;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class TelegramBotHandler extends TelegramLongPollingBot {
    private String botUsername = "firsttgbot123_bot";
    private String botToken = "6836114530:AAEN2o7MblY6Yz1qS9a9Ux98yVoiO-BN-Q0";

    private TelegramBotLogic telegramBotLogic = new TelegramBotLogic();

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage messageToUser = new SendMessage();

        long chatId = 0;
        String textFromUser = "";

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            textFromUser = update.getMessage().getText();

            telegramBotLogic.processTextMessageFromUser(textFromUser, messageToUser);

        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            textFromUser = update.getCallbackQuery().getData();

            telegramBotLogic.processInlineButtonClickFromUser(textFromUser, messageToUser);
        }

        messageToUser.setChatId(chatId);

        try {
            execute(messageToUser);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
