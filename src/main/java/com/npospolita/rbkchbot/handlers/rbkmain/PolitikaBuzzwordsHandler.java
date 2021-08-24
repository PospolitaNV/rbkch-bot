package com.npospolita.rbkchbot.handlers.rbkmain;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.handlers.CommonTextMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PolitikaBuzzwordsHandler extends CommonTextMessageHandler {

    private final TelegramApi telegramApi;
    private final static ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
    private List<String> phrases = List.of(
            "Пошёл ты в политикач, душнила ((",
            "Как же вы заебали со своей политикой ((",
            "Я просто хочу деградировать ((",
            "Когда же ты заткнёшься ((",
            "ОТКРОЙТЕ ФОРТОЧКУ УЖЕ"
    );
    private final static Pattern buzzwordsPattern = Pattern.compile("\\bСССР\\b" +
                                                                    "|\\bКрым" +
                                                                    "|\\bКПРФ" +
                                                                    "|\\bСталин" +
                                                                    "|\\bЛенин" +
                                                                    "|\\bПутин" +
                                                                    "|\\bХрущёв" +
                                                                    "|\\bPootin" +
                                                                    "|\\bАмерика",
            Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);

    @Override
    public Result handle(Update update) {
        if (threadLocalRandom.nextBoolean()) { // more fluctuations
            telegramApi.sendMessage(update, phrases.get(threadLocalRandom.nextInt(phrases.size() - 1)));
        }
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
               && buzzwordsPattern.matcher(update.message().text()).find();
    }
}
