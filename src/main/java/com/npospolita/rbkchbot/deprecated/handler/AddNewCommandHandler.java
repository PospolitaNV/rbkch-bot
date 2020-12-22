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
public class AddNewCommandHandler implements Handler {

    private static final String COMMAND = "/add";

    private final TelegramApi api;

    @Value("${my.id}")
    Integer myId;

    @Override
    public boolean canHandle(Update update) {
        Message message = update.message();
        return !StringUtils.isEmpty(message.text())
                && (message.text().startsWith(COMMAND) && update.message().from().id().equals(myId));
    }

    @Override
    public void handle(Update update) {
        String text = update.message().text();
        String[] tokens = text.split(" ");
        api.addCommand(tokens[1], tokens[2]);
    }
}
