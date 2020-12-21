package com.npospolita.rbkchbot;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {"server.port=8080", "telegram.bot.token=test"})
public abstract class TestBase {
}
