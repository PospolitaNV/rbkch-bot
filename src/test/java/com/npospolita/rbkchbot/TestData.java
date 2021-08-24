package com.npospolita.rbkchbot;

import com.npospolita.rbkchbot.domain.constant.Command;
import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Update;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicInteger;

import static com.pengrad.telegrambot.model.Chat.Type.Private;
import static com.pengrad.telegrambot.model.Chat.Type.supergroup;

@Slf4j
@Builder
public class TestData {

    @Getter
    @RequiredArgsConstructor
    public enum ChatType {
        WORKING(-1L, supergroup), WORKING_2(-2L, supergroup), PERSONAL(-10L, Private), OTHER(-100L, Chat.Type.group);

        private final Long chatId;
        private final Chat.Type chatType;
    }

    public enum Action {
        LEFT_CHAT, JOINED_CHAT, POSTED_MESSAGE
    }

    public enum User {
        ADMIN, SIMPLE
    }


    public static final AtomicInteger updateIncrement = new AtomicInteger(0);
    public static final AtomicInteger messageIncrement = new AtomicInteger(0);

    @Builder.Default
    public ChatType chatType = ChatType.WORKING;
    @Builder.Default
    public Command command = null;
    @Builder.Default
    public String text = null;
    @Builder.Default
    public Action action = Action.POSTED_MESSAGE;
    @Builder.Default
    public User user = User.SIMPLE;

    //todo сделать нормальный билдер
    @SneakyThrows
    public Update toUpdate() {
        JSONObject update = new JSONObject()
                .put("update_id", updateIncrement.incrementAndGet())
                .put("message", new JSONObject()
                        .put("chat", new JSONObject()
                                .put("id", chatType.getChatId())
                                .put("title", chatType.name())
                                .put("type", chatType.getChatType()))
                        .put("message_id", messageIncrement.incrementAndGet()));

        processAction(update);
        processChatType(update);
        processCommandAndText(update);
        return BotUtils.parseUpdate(update.toString());
    }

    private void processCommandAndText(JSONObject update) throws JSONException {
        JSONObject message = update.getJSONObject("message");
        if (command != null) {
            message.put("text", command.getCommand() + "-" + text);
        } else {
            message.put("text", text);
        }
    }

    private void processChatType(JSONObject update) throws JSONException {
        update.getJSONObject("message")
                .put("chat", new JSONObject()
                    .put("id", chatType.getChatId())
                    .put("title", chatType.name())
                    .put("type", chatType.getChatType()));
    }

    private void processAction(JSONObject update) throws JSONException {
        JSONObject message = update.getJSONObject("message");

        switch (action) {
            case LEFT_CHAT:
                message.put("left_chat_member", user());
                break;
            case JOINED_CHAT:
                message.put("new_chat_members", new JSONArray().put(user()));
                break;
            case POSTED_MESSAGE:
                switch (user) {
                    case ADMIN:
                        message.put("from", admin());
                        break;
                    case SIMPLE:
                        message.put("from", user());
                        break;
                }
                break;
        }
    }

    private JSONObject user() throws JSONException {
        return new JSONObject()
                .put("id", 52345)
                .put("is_bot", false)
                .put("first_name", "User")
                .put("username", "UserName");
    }

    private JSONObject admin() throws JSONException {
        return new JSONObject()
                .put("id", 23452397)
                .put("is_bot", false)
                .put("first_name", "Admin")
                .put("username", "AdminUsername");
    }


}
