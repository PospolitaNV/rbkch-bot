package com.npospolita.rbkchbot.deprecated.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

//@Component
@RequiredArgsConstructor
public class AreYouRobotHandler implements Handler {

    private final TelegramApi api;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return !StringUtils.isEmpty(message.text())
                && !message.from().isBot()
                && message.text().equalsIgnoreCase("ты робот?");
    }

    @Override
    public void handle(Update update) {
        api.sendMessage(update, "Я календарь");
    }

}
