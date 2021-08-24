package com.npospolita.rbkchbot.handlers.common;

import com.npospolita.rbkchbot.api.TelegramApi;
import com.npospolita.rbkchbot.domain.WorkingChat;
import com.npospolita.rbkchbot.handlers.NameChangedHandler;
import com.npospolita.rbkchbot.handlers.Result;
import com.npospolita.rbkchbot.repo.WorkingChatRepository;
import com.pengrad.telegrambot.model.Update;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChangedGroupNameHandler extends NameChangedHandler {

    private final TelegramApi telegramApi;
    private final WorkingChatRepository workingChatRepository;

    @Override
    public Result handle(Update update) {
        Optional<WorkingChat> chat = workingChatRepository.findById(update.message().chat().id());
        if (chat.isEmpty()) {
            return Result.STOP;
        }
        WorkingChat workingChat = chat.get();
        telegramApi.sendMessage(update, "Старое название чата: \"" + workingChat.getName() + "\"");
        workingChat.setName(update.message().newChatTitle());
        workingChatRepository.save(workingChat);
        return Result.STOP;
    }

}
