package com.npospolita.rbkchbot;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;

public class TestData {

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
                "      \"id\": 123,\n" +
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
                "      \"id\": 123,\n" +
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

}
