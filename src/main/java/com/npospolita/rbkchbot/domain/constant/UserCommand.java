package com.npospolita.rbkchbot.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserCommand implements Command {

    ABOUT("/about", "Получить информацию о боте"),
    RETRA("/retra", "Информация и ресурсы по ретроспективе"),
    CHAT_LIST("/chats", "Список тематических чатиков РБКача");

    private final String command;
    private final String description;
}
