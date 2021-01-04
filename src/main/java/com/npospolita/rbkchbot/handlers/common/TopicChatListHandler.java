package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.TopicChat;
import com.npospolita.rbkchbot.domain.constant.UserCommand;
import com.npospolita.rbkchbot.handlers.CommonMessageHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.service.ChatService;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Order
@Component
@RequiredArgsConstructor
public class TopicChatListHandler extends CommonMessageHandler {

    private static final UserCommand command = UserCommand.CHAT_LIST;

    private final TelegramApi telegramApi;
    private final ChatService chatService;

    @Override
    public Result handle(Update update) {
        StringBuilder sb = new StringBuilder();
        for (TopicChat topicChat : chatService.getTopicChatList()) {
            sb.append("[").append(topicChat.getDescription()).append("]")
                    .append("(").append(topicChat.getLink()).append(")\n");
        }
        telegramApi.sendMessage(update, sb.toString());
        return Result.STOP;
    }

    @Override
    public boolean canHandle(Update update) {
        return super.canHandle(update) && update.message().text().startsWith(command.getCommand());
    }
}
