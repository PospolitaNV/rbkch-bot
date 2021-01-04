package com.npospolita.rbkchbot;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

//todo better test data
public class TestData {

    public static final Long DEFAULT_CHAT_ID = -23123123123123L;

    public static Update getSimpleMessageUpdate() {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": -23123123123123,\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"block the news\"\n" +
                "  }\n" +
                "}");
    }


    public static Update getSimpleMessageUpdateWithCommand(String command) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": -23123123123123,\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"" + command + " block the news\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getAdminMessageUpdate() {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 23452397,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": -23123123123123,\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"block the news\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getAdminMessageUpdateWithCommand(String command) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 23452397,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": -23123123123123,\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"" + command + "\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getSimpleMessageUpdateWithChat(Long chatId) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": " + chatId + ",\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"block the news\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getSimpleMessageUpdateWithChatAndCommand(String command, Long chatId) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"from\": {\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": " + chatId + ",\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"" + command + "\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getLeftChatUpdateWithChat(Long chatId) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"left_chat_member\": {\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    },\n" +
                "    \"chat\": {\n" +
                "      \"id\": " + chatId + ",\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"block the news\"\n" +
                "  }\n" +
                "}");
    }

    public static Update getNewChatMemberUpdateWithChat(Long chatId) {
        return BotUtils.parseUpdate("{\n" +
                "  \"update_id\": 874199391,\n" +
                "  \"message\": {\n" +
                "    \"message_id\": 33111,\n" +
                "    \"new_chat_members\": [{\n" +
                "      \"id\": 1231231231,\n" +
                "      \"is_bot\": false,\n" +
                "      \"first_name\": \"RRRR\",\n" +
                "      \"username\": \"RRRR54321\"\n" +
                "    }],\n" +
                "    \"chat\": {\n" +
                "      \"id\": " + chatId + ",\n" +
                "      \"title\": \"hhh iiiiii ccccc\",\n" +
                "      \"type\": \"supergroup\"\n" +
                "    },\n" +
                "    \"date\": 1579958705,\n" +
                "    \"text\": \"block the news\"\n" +
                "  }\n" +
                "}");
    }

}
