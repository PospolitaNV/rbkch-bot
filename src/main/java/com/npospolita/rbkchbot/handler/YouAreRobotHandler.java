package com.npospolita.rbkchbot.handler;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
public class YouAreRobotHandler implements Handler {

    private final TelegramApi api;

    private static final ThreadLocalRandom random = ThreadLocalRandom.current();

    @Override
    public boolean canHandle(Update update) {
        return !update.message().from().isBot() && Double.compare(random.nextDouble(), 0.005) < 0;
    }

    @Override
    public void handle(Update update) {
        api.sendMessage(update, "ты робот?");
    }

}
