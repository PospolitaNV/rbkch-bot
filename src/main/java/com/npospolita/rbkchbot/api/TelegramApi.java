package com.npospolita.rbkchbot.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.GetMyCommands;
import com.pengrad.telegrambot.request.SendDice;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SendSticker;
import com.pengrad.telegrambot.request.SetMyCommands;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetMyCommandsResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.sun.istack.NotNull;
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
        sendMessage(update.message().chat().id(), update.message().messageId(), text, parseMode, false, true);
    }

    public void sendMessage(Update update, String text) {
        sendMessage(update.message().chat().id(), update.message().messageId(), text, ParseMode.Markdown, false, true);
    }

    public void sendMessageWithPreview(Update update, String text) {
        sendMessage(update.message().chat().id(), update.message().messageId(), text, ParseMode.Markdown, true, true);
    }

    public void sendMessage(Long chatId, String text) {
        sendMessage(chatId, null, text, ParseMode.Markdown, false, false);
    }

    private void sendMessage(@NotNull Long chatId, Integer replyToId, @NotNull String text, ParseMode parseMode, boolean withPreview, boolean withReply) {
            SendMessage request = new SendMessage(chatId, text)
                    .parseMode(parseMode == null ? ParseMode.Markdown : parseMode)
                    .disableWebPagePreview(!withPreview)
                    .disableNotification(true);

            if (withReply) {
                request.replyToMessageId(replyToId);
            }

            SendResponse response = bot.execute(request);

            if (!response.isOk()) {
                log.error("error: {}", response);
            }
    }

    public void sendImage(Update update, String url) {
        SendPhoto request = new SendPhoto(update.message().chat().id(), url)
                .disableNotification(true);

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

    public void removeCommand(String command) {
        List<BotCommand> botCommands = getCommands();

        botCommands.removeIf(botCommand -> botCommand.command().equals(command));

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
