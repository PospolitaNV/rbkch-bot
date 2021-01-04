package com.npospolita.rbkchbot.service;

import com.npospolita.rbkchbot.handlers.Handler;
import com.npospolita.rbkchbot.handlers.Result;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateProcessService {

    private final List<Handler> handlers;

    public void process(Update update) {
        log.info("Update received: {}", update);
        for (Handler handler : handlers) {
            if (!handler.canHandle(update)) continue;

            log.info("Processing update with handler: {}", handler);
            if (handler.handle(update) == Result.STOP) break;
        }
        log.info("Processing finished");
    }
}
