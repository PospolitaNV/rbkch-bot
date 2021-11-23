package com.npospolita.rbkchbot.handlers.rbkmain;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.CommonTextMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InoagentHandler extends CommonTextMessageHandler {

    private final TelegramApi telegramApi;

    private static final double PROBABILITY = 0.1;

    @Value("${inoagent.users}")
    private List<Integer> inoagents;

    @Value("${inoagent.text}")
    private String inoagentText;

    @Override
    public Result handle(Update update) {
        if (inoagents.contains(update.message().from().id()) &&
                Math.random() < PROBABILITY) {
            telegramApi.sendMessage(update, inoagentText);
        }
        return Result.STOP;
    }
}
