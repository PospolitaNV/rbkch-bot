package com.npospolita.rbkchbot.handlers.rbkmain;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.CommonTextMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PolitikaBuzzwordsHandler extends CommonTextMessageHandler {

    private final TelegramApi telegramApi;
    private final static Pattern buzzwordsPattern = Pattern.compile("\\bСССР\\b" +
                                                                    "|\\bКрым\\b" +
                                                                    "|\\bСталин" +
                                                                    "|\\bЛенин\\b" +
                                                                    "|\\bПутин\\b" +
                                                                    "|\\bамерика", Pattern.CASE_INSENSITIVE)

    @Override
    public Result handle(Update update) {
        telegramApi.sendMessage(update, "Пошёл ты в политикач, душнила ((");
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
               && buzzwordsPattern.matcher(update.message().text()).find();
    }
}
