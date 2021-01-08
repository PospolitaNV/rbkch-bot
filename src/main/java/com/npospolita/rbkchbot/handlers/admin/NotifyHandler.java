package com.npospolita.rbkchbot.handlers.admin;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.WorkingChat;
import com.npospolita.rbkchbot.domain.constant.AdminCommand;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

@Slf4j
@Order
@Component
@RequiredArgsConstructor
public class NotifyHandler extends AdminMessageHandler {

    private static final AdminCommand command = AdminCommand.NOTIFY;

    private final TelegramApi api;
    private final WorkingChatRepository workingChatRepository;

    @Override
    public Result handle(Update update) {
        String[] tokens = update.message().text().split("-");
        if (tokens.length != 3) {
            api.sendMessage(update, command.getUsage());
        } else {
            String[] tags = tokens[1].split(",");
            for (WorkingChat workingChat : workingChatRepository.findAllByTagIsIn(Arrays.asList(tags))) {
                api.sendMessage(workingChat.getId(), tokens[2]);
            }
        }

        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update)
                && StringUtils.hasText(update.message().text())
                && update.message().text().startsWith(command.getCommand());
    }
}
