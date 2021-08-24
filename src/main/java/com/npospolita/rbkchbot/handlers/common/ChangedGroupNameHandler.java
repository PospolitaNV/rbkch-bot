package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.NameChangedHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangedGroupNameHandler extends NameChangedHandler {

    private final TelegramApi telegramApi;

    @Override
    public Result handle(Update update) {
        telegramApi.sendMessage(update, "Старое название чата: \"" + update.message().chat().title() + "\"");
        return Result.STOP;
    }

}
