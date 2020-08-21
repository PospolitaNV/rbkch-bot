package com.npospolita.rbkchbot.handler;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetraHandler implements Handler {

    private static final String COMMAND = "/retra";

    private final TelegramBot bot;

    @Value("${rbkch.retra.link}")
    String retraLink;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return !StringUtils.isEmpty(message.text())
                && (message.text().contains(COMMAND));
    }

    @Override
    public void handle(Update update) {
        Message message = update.message();

        SendMessage request = new SendMessage(message.chat().id(), retraLink)
                .parseMode(ParseMode.HTML)
                .disableWebPagePreview(true)
                .disableNotification(true)
                .replyToMessageId(message.messageId());

        SendResponse sendResponse;
        do {
            sendResponse = bot.execute(request);
        } while (!sendResponse.isOk());
    }

}
