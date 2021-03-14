package com.npospolita.rbkchbot.handlers.rbkmain;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.handlers.CommonTextMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VariCommandHandler extends CommonTextMessageHandler {

    private static final String RANDOM_IMAGE_URL = "https://source.unsplash.com/random";
    private static final UserCommand command = UserCommand.VARI;

    private final TelegramApi telegramApi;

    @Override
    public Result handle(Update update) {
        telegramApi.sendImage(update, RANDOM_IMAGE_URL);
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(command.getCommand());
    }
}
