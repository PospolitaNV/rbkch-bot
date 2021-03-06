package com.npospolita.rbkchbot.config;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class BotConfig {

    @Value("${hosted.url}")
    String url;

    @Bean
    public TelegramBot telegramBot(@Value("${telegram.bot.token}") String botToken) {
        TelegramBot telegramBot = new TelegramBot(botToken);
        initWebhook(telegramBot, botToken);
        return telegramBot;
    }

    private void initWebhook(TelegramBot telegramBot, String botToken) {
        BaseResponse response = telegramBot.execute(new SetWebhook().url(url + botToken));

        if (response.isOk()) {
            log.info("Webhook successfully set: {}", response);
        } else {
            log.error("Error while setting a webhook: {}", response);
        }
    }
}
