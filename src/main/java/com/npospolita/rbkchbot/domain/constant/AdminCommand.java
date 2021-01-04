package com.npospolita.rbkchbot.domain.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AdminCommand implements Command {
    CHAT_ADD(
            "/add_chat",
            "Добавить чат в список тематических чатов",
            "Command usage: {/add_chat}-{description}-{link}",
            "Chat added successfully."
    ),
    COMMAND_ADD(
            "/add_command",
            "Добавить команду в список доступных пользователем команд",
            "Command usage: {/add_command}-{command}-{description}",
            "Command added successfully."
    ),
    COMMAND_REMOVE(
            "/remove_command",
            "Удалить команду из списка доступных пользователем команд",
            "Command usage: {/remove_command}-{command}",
            "Command removed successfully."
    ),
    ENABLE_CHAT_SUPPORT(
            "/enable",
            "Включить бота в этом чате",
            "Command usage: {/enable}-{tag}",
            "Chat enabled."
    ),
    DISABLE_CHAT_SUPPORT(
            "/disable",
            "Отключить бота в этом чате",
            "Command usage: {/disable}",
            "Chat disabled."
    );

    private final String command;
    private final String description;
    private final String usage;
    private final String response;

}