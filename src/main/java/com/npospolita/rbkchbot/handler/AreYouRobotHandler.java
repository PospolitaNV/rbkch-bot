package com.npospolita.rbkchbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendSticker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
public class AreYouRobotHandler implements Handler {

    private final TelegramBot bot;

    private static final String TAKI_DA_STICKER_FILE_ID = "CAACAgIAAxkBAAMeXz0jNewZB_tO6284d688zViwKPsAAjMBAALdEPsV4z9z-2MJv8AbBA";

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return !StringUtils.isEmpty(message.text())
                && !message.from().isBot()
                && message.text().equalsIgnoreCase("ты робот?");
    }

    @Override
    public void handle(Update update) {
        Message message = update.message();

        SendSticker request = new SendSticker(message.chat().id(), TAKI_DA_STICKER_FILE_ID)
                .disableNotification(true)
                .replyToMessageId(message.messageId());

        bot.execute(request);
    }

}
