package com.npospolita.rbkchbot.listener;

import com.npospolita.rbkchbot.handler.Handler;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
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

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllUpdatesListener implements UpdatesListener {

    @Value("${rbkch.chat.id}")
    Long chatId;

    @Value("${my.id}")
    Integer myId;

    private final TelegramBot bot;
    private final List<Handler> handlers;

    @PostConstruct
    public void wire() {
        bot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> list) {
        log.info("updates: {}", list);

        final List<Update> updates = list.stream()
                .filter(update -> update.message() != null)
                .filter(update -> fromChat(update)
                    || fromMe(update))
                .collect(Collectors.toList());

        for (Update update : updates) {
            for (Handler handler : handlers) {
                if (handler.canHandle(update)) {
                    handler.handle(update);
                }
            }
        }

        return CONFIRMED_UPDATES_ALL;
    }

    private boolean fromChat(Update update) {
        return update.message().chat().id().equals(chatId);
    }

    private boolean fromMe(Update update) {
        return update.message().from().id().equals(myId);
    }

}
