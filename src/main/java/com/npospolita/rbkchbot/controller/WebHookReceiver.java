package com.npospolita.rbkchbot.controller;

import com.npospolita.rbkchbot.service.UpdateProcessService;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class WebHookReceiver {

    private final UpdateProcessService updateProcessService;

    @PostMapping(path = "/${telegram.bot.token}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void receiveWebHook(@RequestBody String request) {
        log.info("Received update: {}", request);
        Update update = BotUtils.parseUpdate(request);
        log.info("Update parsed: {}", update);
        updateProcessService.process(update);
    }

}
