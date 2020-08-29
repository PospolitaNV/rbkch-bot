package com.npospolita.rbkchbot.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.*;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMyCommandsResponse;
import com.pengrad.telegrambot.response.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramApi {

    private final TelegramBot bot;

    public void sendMessage(Update update, String text, ParseMode parseMode) {
        sendMessage(update.message(), text, parseMode);
    }

    public void sendMessage(Update update, String text) {
        sendMessage(update.message(), text, ParseMode.Markdown);
    }

    public void sendMessage(Message message, String text, ParseMode parseMode) {
        SendMessage request = new SendMessage(message.chat().id(), text)
                .parseMode(parseMode)
                .disableWebPagePreview(false)
                .disableNotification(true)
                .replyToMessageId(message.messageId());

        SendResponse response = bot.execute(request);

        if (!response.isOk()) {
            log.error("error: {}", response);
        }
    }

    public void sendSticker(Update update, String stickerFileId) {
        Message message = update.message();

        SendSticker request = new SendSticker(message.chat().id(), stickerFileId)
                .disableNotification(true)
                .replyToMessageId(message.messageId());

        SendResponse response = bot.execute(request);

        if (!response.isOk()) {
            log.error("error: {}", response);
        }
    }

    public void sendDice(Update update) {
        SendDice emoji = new SendDice(update.message().chat().id())
                .emoji("🎲")
                .replyToMessageId(update.message().messageId());

        SendResponse response = bot.execute(emoji);

        if (!response.isOk()) {
            log.error("error: {}", response);
        }

        if (response.message().dice().value() == 6) {
            sendMessage(response.message(), "Ебать как могу", ParseMode.Markdown);
        }
    }

    public void addCommand(String command, String description) {
        List<BotCommand> botCommands = getCommands();

        botCommands.add(new BotCommand(command, description));

        SetMyCommands setMyCommands = new SetMyCommands(botCommands.toArray(new BotCommand[0]));

        BaseResponse response = bot.execute(setMyCommands);

        if (!response.isOk()) {
            log.error("error: {}", response);
        }
    }

    public List<BotCommand> getCommands() {
        GetMyCommands getMyCommands = new GetMyCommands();

        GetMyCommandsResponse commandsResponse = bot.execute(getMyCommands);

        return new ArrayList<>(Arrays.asList(commandsResponse.commands()));
    }
}
