package com.npospolita.rbkchbot.deprecated.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class RetraHandler implements Handler {

    private static final String COMMAND = "/retra";

    private final TelegramApi api;

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
        api.sendMessage(update, retraLink);
    }

}
