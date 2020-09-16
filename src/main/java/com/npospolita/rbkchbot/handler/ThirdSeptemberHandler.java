package com.npospolita.rbkchbot.handler;


import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.Month;

@Component
@RequiredArgsConstructor
public class ThirdSeptemberHandler implements Handler{

    private final TelegramApi api;

    private static final String VIDEO_URL = "https://www.youtube.com/watch?v=C-5t1DLMYV8";

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        if (StringUtils.isEmpty(message.text()) || message.from().isBot()) {
            return false;
        }
        LocalDateTime now = LocalDateTime.now();
        String text = message.text().toLowerCase();
        boolean isThirdSeptember = now.getMonth() == Month.SEPTEMBER && now.getDayOfMonth() == 3;
        boolean containsTriggerWords = text.contains("календарь")
                || text.contains("шафутинск")
                || text.contains("3 сентября")
                || text.contains("третье сентября");
        return isThirdSeptember && containsTriggerWords;
    }

    @Override
    public void handle(Update update) {
        api.sendMessageWithPreview(update.message(), VIDEO_URL);
    }
}
